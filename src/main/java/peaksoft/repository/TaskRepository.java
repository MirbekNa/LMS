package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.TaskResponse;
import peaksoft.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select new peaksoft.dto.response.TaskResponse(t.id,t.taskName,t.taskText,t.deadLine) from Task t ")
    List<TaskResponse> findAllTasks();
    TaskResponse findTaskById(Long id);
}
