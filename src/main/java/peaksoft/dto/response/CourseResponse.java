package peaksoft.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CourseResponse(Long id, String courseName, LocalDate dateOfStart, String description) {
    public CourseResponse(Long id, String courseName, LocalDate dateOfStart, String description) {
        this.id = id;
        this.courseName = courseName;
        this.dateOfStart = dateOfStart;
        this.description = description;
    }


}
