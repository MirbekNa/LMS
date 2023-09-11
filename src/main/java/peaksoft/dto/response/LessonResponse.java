package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record LessonResponse(Long id,String lessonName) {
    public LessonResponse(Long id, String lessonName) {
        this.id = id;
        this.lessonName = lessonName;
    }
}
