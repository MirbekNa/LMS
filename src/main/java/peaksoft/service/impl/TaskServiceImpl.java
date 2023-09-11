package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.TaskRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.TaskResponse;
import peaksoft.entity.Lesson;
import peaksoft.entity.Task;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.LessonRepository;
import peaksoft.repository.TaskRepository;
import peaksoft.service.TaskService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final LessonRepository lessonRepository;

    @Override
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAllTasks();
    }

    @Override
    public SimpleResponse saveTaskToLesson(Long lessonId, TaskRequest taskRequest) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() ->
                new NotFoundException("Lesson with id:" + lessonId + " not found !!!"));
        Task task = new Task();
        task.setTaskName(taskRequest.taskName());
        task.setTaskText(taskRequest.taskText());
        task.setDeadLine(taskRequest.deadLine());
        task.setLesson(lesson);
        taskRepository.save(task);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Task with id:" + lesson.getId() + " successfully saved ☺ to Lesson with id:" + lessonId)
                .build();
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        return taskRepository.findTaskById(id);
    }

    @Override
    public SimpleResponse updateTaskWithLesson(Long lessonId, Long id, TaskRequest taskRequest) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() ->
                new NotFoundException("Lesson with id:" + lessonId + " not found !!!"));
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Task with id:" + id + " not found !!!"));
        task.setTaskName(taskRequest.taskName());
        task.setTaskText(taskRequest.taskText());
        task.setDeadLine(taskRequest.deadLine());
        task.setLesson(lesson);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Task with id:" + id + "successfully updated ☺ along with Lesson with id:" + lessonId)
                .build();
    }

    @Override
    public SimpleResponse deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Task with id:" + id + " not found !!!"));
        taskRepository.delete(task);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Task with id:" + id + " successfully deleted ☺ ")
                .build();
    }

    @Override
    public SimpleResponse saveTask(TaskRequest taskRequest) {
        Task task=new Task();
        task.setTaskName(taskRequest.taskName());
        task.setTaskText(taskRequest.taskText());
        task.setDeadLine(taskRequest.deadLine());
        taskRepository.save(task);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Task with id:" + task.getId() + " successfully saved ☺ ")
                .build();
    }
}
