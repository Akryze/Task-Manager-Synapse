package com.example.Synapse.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TaskDTO implements java.io.Serializable {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String username; // Вместо целого объекта User передаем только строку
    private String createdAt;  // Сразу отформатируем дату в строку для красоты
}