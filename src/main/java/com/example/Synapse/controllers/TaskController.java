package com.example.Synapse.controllers;

import com.example.Synapse.models.Attachment;
import com.example.Synapse.models.Status;
import com.example.Synapse.models.Task;
import com.example.Synapse.repositories.AttachmentsRepository;
import com.example.Synapse.services.AttachmentService;
import com.example.Synapse.services.TaskService;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final AttachmentService attachmentService;
    private final AttachmentsRepository attachmentsRepository;
    private final MinioClient minioClient;
    @Value("${minio.bucket}")
    private String bucketName;


    @GetMapping("/task/{id}")
    public String taskInfo(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        model.addAttribute("attachments", attachmentService.getAttachmentsByTaskId(id));
        model.addAttribute("allStatuses", taskService.getAllStatuses());
        return "task-info";
    }

    @GetMapping("/")
    public String tasks(@RequestParam(name = "title", required = false) String title,
                        Model model,
                        Principal principal) {
        model.addAttribute("tasks", taskService.listOfTasks(title, principal.getName()));
        model.addAttribute("user", principal.getName());
        return "tasks";
    }

    @PostMapping("/task/create")
    public String createTask(Task task, Principal principal) {
        taskService.saveTask(task, principal.getName());
        return "redirect:/";
    }

    @PostMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        attachmentService.deleteAllAttachments(id);
        taskService.deleteTask(id);
        return "redirect:/";
    }

    @PostMapping("/task/upload/{id}")
    public String uploadAttachment(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/task/" + id;
        }
        attachmentService.uploadFile(file, id);
        return "redirect:/task/" + id;
    }

    @PostMapping("/task/{id}/desc-update")
    public String descriptionUpdate(@PathVariable Long id,
                                    Principal principal,
                                    @RequestParam(value = "task-description", required = false, defaultValue = "") String desc
                                    ) {

        taskService.descriptionUpdate(id, principal.getName(), desc);
        return "redirect:/task/" + id;
    }

    @PostMapping("/task/{id}/task-name-update")
    public String taskNameUpdate(@PathVariable Long id,
                                    Principal principal,
                                    @RequestParam(value = "task-name", required = false, defaultValue = "") String taskName
    ) {

        taskService.taskNameUpdate(id, principal.getName(), taskName);
        return "redirect:/task/" + id;
    }

    @PatchMapping("/task/{id}/status-update")
    public String statusUpdate(@PathVariable Long id,
                               Principal principal,
                               @RequestParam("status") Status status) {

        taskService.statusChange(id, principal.getName(), status);
        return "redirect:/task/" + id;
    }


}
