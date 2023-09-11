package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.LessonRequest;
import peaksoft.dto.response.LessonResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Course;
import peaksoft.entity.Lesson;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CourseRepository;
import peaksoft.repository.LessonRepository;
import peaksoft.service.LessonService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<LessonResponse> getAllLessons() {
        return lessonRepository.findAllLessons();
    }

    @Override
    public SimpleResponse saveLessonToCourse(Long courseId, LessonRequest lessonRequest) {
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new NotFoundException("Course with id:" + courseId + " not found!!!"));
        Lesson lesson = new Lesson();
        lesson.setLessonName(lessonRequest.lessonName());
        lesson.setCourse(course);
        lessonRepository.save(lesson);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Lesson with id:" + lesson.getId() + " successfully saved ☺ to Course with id" + courseId)
                .build();
    }

    @Override
    public SimpleResponse updateLesson(Long id, LessonRequest lessonRequest) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Lesson with id:" + id + " not found !!!"));
        lesson.setLessonName(lessonRequest.lessonName());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Lesson with id:" + id + " successfully updated ☺")
                .build();
    }

    @Override
    public SimpleResponse updateLessonWithCourse(Long courseId, Long id, LessonRequest lessonRequest) {
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new NotFoundException("Course with id:" + courseId + " not found!!!"));
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Lesson with id:" + id + " not found!!!"));
        lesson.setLessonName(lessonRequest.lessonName());
        lesson.setCourse(course);
        lessonRepository.save(lesson);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Lesson with id:" + id + " successfully updated with Course with id:" + courseId)
                .build();
    }

    @Override
    public SimpleResponse deleteLesson(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Lesson with id:" + id + " not found!!!"));
        lessonRepository.delete(lesson);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Lesson with id:" + id + " successfully ☺ deleted")
                .build();
    }

    @Override
    public SimpleResponse saveLesson(LessonRequest lessonRequest) {
        Lesson lesson = new Lesson();
        lesson.setLessonName(lessonRequest.lessonName());
        lessonRepository.save(lesson);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Lesson with id:" + lesson.getId() + " successfully ☺ saved ")
                .build();
    }
}
