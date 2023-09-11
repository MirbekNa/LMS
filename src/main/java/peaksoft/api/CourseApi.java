package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.pagination.CoursePagination;
import peaksoft.dto.request.CourseRequest;
import peaksoft.dto.response.CourseResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseApi {
    private final CourseService courseService;

    @PermitAll
    @GetMapping
    List<CourseResponse> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{companyId}/company")
    SimpleResponse saveCourseToCompany(@PathVariable Long companyId,
                                       @RequestBody CourseRequest courseRequest) {
        return courseService.saveCourseToCompany(companyId, courseRequest);
    }

    @PermitAll
    @GetMapping("/{id}")
    CourseResponse getCourseById(@PathVariable Long id) {
        return courseService.findCourseById(id);
    }

    @PermitAll
    @GetMapping("/dateOfStartSortCourse/news")
    List<CourseResponse> dateOfStartCourseNew() {
        return courseService.dateOfStartCourseNew();
    }

    @PermitAll
    @GetMapping("dateOfStartSortCourse/owns")
    List<CourseResponse> dateOfStartSortCourse() {
        return courseService.dateOfStartCourseOwns();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    SimpleResponse updateCourse(@PathVariable Long id,
                                @RequestBody CourseRequest courseRequest) {
        return courseService.updateCourse(id, courseRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}")
    SimpleResponse deleteCourse(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }

    @PermitAll
    @GetMapping("/pagination")
    CoursePagination getAllCoursePagination(@RequestParam int currentPage,
                                            @RequestParam int pageSize) {
        return courseService.getAllCoursesPagination(currentPage, pageSize);

    }
}
