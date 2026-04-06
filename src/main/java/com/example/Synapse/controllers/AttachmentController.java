package com.example.Synapse.controllers;

import com.example.Synapse.models.Attachment;
import com.example.Synapse.repositories.AttachmentsRepository;
import com.example.Synapse.services.AttachmentService;

import io.minio.GetObjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final AttachmentsRepository attachmentsRepository;

    @GetMapping("/api/attachment/{attachmentId}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Long attachmentId) {

        Attachment attachment = attachmentsRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("Файл не найден.."));


        GetObjectResponse getObjectResponse = attachmentService.downloadFile(attachmentId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() + "\"");
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, attachment.getContentType());

        InputStreamResource resource = new InputStreamResource(getObjectResponse);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);

    }

    @PostMapping("/api/attachment/delete/{attachmentId}")
    public String deleteFile(@PathVariable Long attachmentId) {
        Attachment attachment = attachmentsRepository.findById(attachmentId)
                .orElseThrow(()-> new RuntimeException("File not found!"));

        Long taskId = attachment.getTask().getTaskId();

        attachmentService.deleteAttachmentFromMinio(attachment.getS3Key());
        attachmentsRepository.deleteById(attachmentId);


        return "redirect:/task/" + taskId;

   }
}
