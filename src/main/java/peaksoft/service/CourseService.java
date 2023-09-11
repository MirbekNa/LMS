package peaksoft.service;

import peaksoft.dto.pagination.CoursePagination;
import peaksoft.dto.request.CourseRequest;
import peaksoft.dto.response.CourseResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface CourseService {
    List<CourseResponse> getAllCourses();


    CourseResponse findCourseById(Long id);

    SimpleResponse updateCourse(Long id, CourseRequest courseRequest);

    SimpleResponse deleteCourse(Long id);

    SimpleResponse saveCourseToCompany(Long companyId, CourseRequest courseRequest);

    List<CourseResponse> dateOfStartCourseNew();

    List<CourseResponse> dateOfStartCourseOwns();

    CoursePagination getAllCoursesPagination(int currenPage, int pageSize);
}
