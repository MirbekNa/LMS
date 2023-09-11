package peaksoft.service;

import peaksoft.dto.request.LessonRequest;
import peaksoft.dto.response.LessonResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface LessonService {

    List<LessonResponse> getAllLessons();

    SimpleResponse saveLessonToCourse(Long courseId, LessonRequest lessonRequest);

    SimpleResponse updateLesson(Long id, LessonRequest lessonRequest);

    SimpleResponse updateLessonWithCourse(Long courseId, Long id, LessonRequest lessonRequest);

    SimpleResponse deleteLesson(Long id);

    SimpleResponse saveLesson(LessonRequest lessonRequest);
}
