package peaksoft.dto.request;

import lombok.Builder;

@Builder
public record LessonRequest(String lessonName) {
    public LessonRequest(String lessonName) {
        this.lessonName = lessonName;
    }
}
