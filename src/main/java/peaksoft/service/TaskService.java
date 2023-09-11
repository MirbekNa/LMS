package peaksoft.service;

import peaksoft.dto.request.TaskRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {
    List<TaskResponse> getAllTasks();

    SimpleResponse saveTaskToLesson(Long lessonId, TaskRequest taskRequest);

    TaskResponse getTaskById(Long id);

    SimpleResponse updateTaskWithLesson(Long lessonId, Long id, TaskRequest taskRequest);

    SimpleResponse deleteTask(Long id);

    SimpleResponse saveTask(TaskRequest taskRequest);
}
