package peaksoft.dto.request;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record TaskRequest(String taskName, String taskText, LocalDateTime deadLine) {
    public TaskRequest(String taskName, String taskText, LocalDateTime deadLine) {
        this.taskName = taskName;
        this.taskText = taskText;
        this.deadLine = deadLine;
    }
}
