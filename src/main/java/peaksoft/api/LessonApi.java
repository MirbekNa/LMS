package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.LessonRequest;
import peaksoft.dto.response.LessonResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.LessonService;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonApi {
    private final LessonService lessonService;

    @PermitAll
    @GetMapping
    List<LessonResponse> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PostMapping("/save")
    SimpleResponse saveLesson(@RequestBody LessonRequest lessonRequest) {
        return lessonService.saveLesson(lessonRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PostMapping("/{courseId}/course")
    SimpleResponse saveLessonToGroup(@PathVariable Long courseId,
                                     @RequestBody LessonRequest lessonRequest) {
        return lessonService.saveLessonToCourse(courseId, lessonRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PutMapping("/{id}")
    SimpleResponse updateLesson(@PathVariable Long id, @RequestBody LessonRequest lessonRequest) {
        return lessonService.updateLesson(id, lessonRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PutMapping("/{courseId}/course/{id}")
    SimpleResponse updateLessonWithCourse(@PathVariable Long courseId, @PathVariable Long id, @RequestBody LessonRequest lessonRequest) {
        return lessonService.updateLessonWithCourse(courseId, id, lessonRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PostMapping("/{id}")
    SimpleResponse deleteLesson(@PathVariable Long id) {
        return lessonService.deleteLesson(id);
    }
}
