package com.example.Synapse.services;

import com.example.Synapse.dto.TaskDTO;
import com.example.Synapse.models.Status;
import com.example.Synapse.models.Task;
import com.example.Synapse.models.User;
import com.example.Synapse.repositories.TaskRepository;
import com.example.Synapse.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldSaveTask() {
      Task task = new Task();
      String username = "Viktorzxc";
      User user = new User();
      user.setUsername(username);

      when(userRepository.findByUsername("Viktorzxc")).thenReturn(Optional.of(user));

      when(taskRepository.save(any(Task.class))).thenReturn(task);

      taskService.saveTask(task, username);
      assertEquals(user, task.getUser());
      assertEquals(Status.NEW, task.getStatus());

      verify(taskRepository).save(task);
    }

    @Test
    void shouldGetTaskById() {

        Long taskId = 1L;
        Task task = new Task();
        task.setTitle("Title1");
        task.setDescription("Descr1");



        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        task.setTaskId(1L);

        // проверка на исключение, если таск айди не найден
        assertEquals(taskId, task.getTaskId());

        TaskDTO result = taskService.getTaskById(task.getTaskId());

        assertNotNull(result, "DTO NOT NULL");
        assertEquals(result.getTitle(), task.getTitle());

    }
}