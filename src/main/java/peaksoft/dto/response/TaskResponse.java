package peaksoft.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder



public record TaskResponse(Long id, String taskName, String taskText, LocalDateTime deadLine) {
    public TaskResponse(Long id, String taskName, String taskText, LocalDateTime deadLine) {
        this.id = id;
        this.taskName = taskName;
        this.taskText = taskText;
        this.deadLine = deadLine;
    }
}
