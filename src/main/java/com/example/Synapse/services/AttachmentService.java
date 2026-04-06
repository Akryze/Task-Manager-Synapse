package com.example.Synapse.services;

import com.example.Synapse.models.Attachment;
import com.example.Synapse.models.Task;
import com.example.Synapse.repositories.AttachmentsRepository;
import com.example.Synapse.repositories.TaskRepository;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.bcel.AtAjAttributes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AttachmentService {

    private final AttachmentsRepository attachmentsRepository;
    private final TaskRepository taskRepository;

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;


    public List <Attachment> getAttachmentsByTaskId(Long taskId) {
        return attachmentsRepository.findByTask_TaskId(taskId);
    }


    public void uploadFile(MultipartFile file, Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .object(fileName)
                    .bucket(bucketName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            Attachment attachment = Attachment.builder()
                    .task(task)
                    .fileName(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .s3Key(fileName)
                    .build();

            attachmentsRepository.save(attachment);


        } catch(Exception e) {
            log.error("Cant upload file", e);
            throw new RuntimeException("Cant save", e);
        }

    }

    public GetObjectResponse downloadFile(Long attachmentId) {
        Attachment attachment = attachmentsRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("Файл не найден"));

        String s3key = attachment.getS3Key();

        try {
           return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(s3key)
                    .build());
        } catch (Exception e) {
            log.error("Ошибка загрузки файла...", e);
            throw new RuntimeException("Ошибка в загрузке", e);
        }
    }

    @Transactional
    public void deleteAllAttachments(Long id) {
        List<Attachment> attachmentsList = attachmentsRepository.findByTask_TaskId(id);
        for(Attachment attachment : attachmentsList) {
            deleteAttachmentFromMinio(attachment.getS3Key());
        }
        attachmentsRepository.deleteByTask_TaskId(id);
    }

    public void deleteAttachmentFromMinio(String s3key) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(s3key)
                    .build());
        } catch (Exception e) {
            log.error("Ошибка удаления файла...", e);
            throw new RuntimeException("Ошибка в удалении", e);
        }
        }
    }

