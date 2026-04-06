package com.example.Synapse.services;

import com.example.Synapse.dto.TaskDTO;
import com.example.Synapse.models.Status;
import com.example.Synapse.models.Task;
import com.example.Synapse.models.User;
import com.example.Synapse.repositories.TaskRepository;
import com.example.Synapse.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");



    private TaskDTO convertToDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getTaskId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().name())
                .username(task.getUser().getUsername())
                .createdAt(task.getCreatedAt() != null ? task.getCreatedAt().format(dateFormatter) : "")
                .build();
    }

    public List<TaskDTO> listOfTasks(String title, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Маппим список сущностей в список DTO через Stream API
        return taskRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void saveTask(Task task, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        task.setUser(user);

        if (task.getStatus() == null) {
            task.setStatus(Status.NEW);
        }

        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }


    @Cacheable(value = "tasks", key = "#id")
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        log.info("===> request in db ID: {}", id);
        return convertToDTO(task);
    }

    @CacheEvict(value = "tasks", key = "#taskId")
    @Transactional
    public void statusChange(Long taskId, String username, Status newStatus) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found: " + taskId));

        if(!task.getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("You can not change a status! ");
        }

        task.setStatus(newStatus);

        taskRepository.save(task);

    }

    public List<Status> getAllStatuses() {
        return Arrays.asList(Status.values());
    }

    @CacheEvict(value = "tasks", key = "#taskId")
    @Transactional
    public void descriptionUpdate(Long taskId, String username, String newDescription) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found: " + taskId));

        if(!task.getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("You can not change a description! ");
        }

        task.setDescription(newDescription);
        taskRepository.save(task);
    }


    @CacheEvict(value = "tasks", key = "#taskId")
    @Transactional
    public void taskNameUpdate(Long taskId, String username, String newTaskName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found: " + taskId));

        if(!task.getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("You can not change a task name!");
        }

        task.setTitle(newTaskName);
        taskRepository.save(task);
    }
}
