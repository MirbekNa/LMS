package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.pagination.CoursePagination;
import peaksoft.dto.request.CourseRequest;
import peaksoft.dto.response.CourseResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Company;
import peaksoft.entity.Course;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CompanyRepository;
import peaksoft.repository.CourseRepository;
import peaksoft.service.CourseService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAllCourses();
    }


    @Override
    public CourseResponse findCourseById(Long id) {
        CourseResponse courseRes = courseRepository.findCourseById(id);
        return courseRes;
    }

    @Override
    public SimpleResponse updateCourse(Long id, CourseRequest courseRequest) {

        Course course = courseRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Course with id:" + id + " not found!!"));
        course.setCourseName(courseRequest.courseName());
        course.setDescription(courseRequest.description());
        course.setDateOfStart(courseRequest.dateOfStart());
        courseRepository.save(course);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Successfully saved Course ☺ with id:" + id)
                .build();
    }

    @Override
    public SimpleResponse deleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Course with id:" + id + " not found!!!"));
        courseRepository.delete(course);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Course with id:" + id + " successfully deleted ☺")
                .build();
    }

    @Override
    public SimpleResponse saveCourseToCompany(Long companyId, CourseRequest courseRequest) {
        Company company = companyRepository.findById(companyId).orElseThrow(() ->
                new NotFoundException("Company with id:" + companyId + " not found !!!"));
        Course course = new Course();
        course.setCourseName(courseRequest.courseName());
        course.setDateOfStart(courseRequest.dateOfStart());
        course.setDescription(courseRequest.description());
        course.setCompany(company);
        courseRepository.save(course);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Course with id:" + course.getId() + " successfully saved ☺ in a Company with id:" + companyId)
                .build();
    }

    @Override
    public List<CourseResponse> dateOfStartCourseNew() {
        return courseRepository.dateOfStartCourseNew();
    }

    @Override
    public List<CourseResponse> dateOfStartCourseOwns() {
        return courseRepository.dateOfStartCourseOwns();
    }

    @Override
    public CoursePagination getAllCoursesPagination(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<CourseResponse> courses = courseRepository.findAllCourses(pageable);

        CoursePagination coursePagination = new CoursePagination();
        coursePagination.setCourses(courses.getContent());
        coursePagination.setCurrentPage(courses.getNumber());
        coursePagination.setPageSize(courses.getTotalPages());
        return coursePagination;
    }


}
