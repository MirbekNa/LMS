package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.TaskRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.TaskResponse;
import peaksoft.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskApi {
    private final TaskService taskService;

    @PermitAll
    @GetMapping
    List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PostMapping("/save")
    SimpleResponse saveTask(@RequestBody TaskRequest taskRequest) {
        return taskService.saveTask( taskRequest);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PostMapping("/{lessonId}/lesson")
    SimpleResponse saveTask(@PathVariable Long lessonId,
                            @RequestBody TaskRequest taskRequest) {
        return taskService.saveTaskToLesson(lessonId, taskRequest);
    }

    @PermitAll
    @GetMapping("/{id}")
    TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PutMapping("/{lessonId}/update/{id}")
    SimpleResponse updateTaskWithTheLesson(@PathVariable Long lessonId,
                                           @PathVariable Long id,
                                           @RequestBody TaskRequest taskRequest) {
        return taskService.updateTaskWithLesson(lessonId, id, taskRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PostMapping("/{id}")
    SimpleResponse deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }
}
