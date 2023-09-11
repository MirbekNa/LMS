package peaksoft.dto.request;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record CourseRequest(String courseName, LocalDate dateOfStart, String description) {
    public CourseRequest(String courseName, LocalDate dateOfStart, String description) {
        this.courseName = courseName;
        this.dateOfStart = dateOfStart;
        this.description = description;
    }
}
