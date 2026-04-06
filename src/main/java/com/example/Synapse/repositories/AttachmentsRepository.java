package com.example.Synapse.repositories;

import com.example.Synapse.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentsRepository extends JpaRepository<Attachment, Long> {
    // 1. Было findByTaskId, стало findByTask_TaskId
    // Подчеркивание говорит: зайти в поле 'task' и найти там 'taskId'
    List<Attachment> findByTask_TaskId(Long taskId);

    // 2. Аналогично для удаления
    void deleteByTask_TaskId(Long taskId);

    List<Attachment> findByS3Key(String s3key);
}
