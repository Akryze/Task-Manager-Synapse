package com.example.Synapse.repositories;

import com.example.Synapse.models.Task;
import com.example.Synapse.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTitle(String title);
    List<Task> findByUser(User user);
}
