package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.InstructorRequest;
import peaksoft.dto.response.AboutInstructor;
import peaksoft.dto.response.CounterStudentByGroup;
import peaksoft.dto.response.InstructorResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.*;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CompanyRepository;
import peaksoft.repository.CourseRepository;
import peaksoft.repository.InstructorRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.InstructorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InstructorServiceImpl implements InstructorService {
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final CompanyRepository companyRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<InstructorResponse> getAllInstructors() {
        return instructorRepository.findAllInstructors();
    }


    @Override
    public InstructorResponse findInstructorById(Long id) {
        return instructorRepository.findInstructorById(id).orElseThrow(() ->
                new NotFoundException("Instructor with id:" + id + " not found !!!"));
    }


    @Override
    public SimpleResponse saveInstructorToCompany(Long companyId, InstructorRequest instructorRequest) {
        Company company = companyRepository.findById(companyId).orElseThrow(() ->
                new NotFoundException("Company with id:" + companyId + " not found !!!"));
        Instructor instructor = new Instructor();
        instructor.setFirstName(instructorRequest.firstName());
        instructor.setLastName(instructorRequest.lastName());
        instructor.setEmail(instructorRequest.email());
        instructor.setPhoneNumber(instructorRequest.phoneNumber());
        instructor.setPassword(passwordEncoder.encode(instructorRequest.password()));
        instructor.setSpecialAction(instructorRequest.specialAction());
        instructor.setRole(instructorRequest.role());
        instructor.setCompanies(List.of(company));
        instructorRepository.save(instructor);
        log.info("Instructor successfully saved to Company with id:" + companyId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully instructor saved  ☺")
                .build();
    }

    @Override
    public SimpleResponse updateInstructor(Long id, InstructorRequest instructorRequest) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Instructor with id:" + id + " not found !!!"));
        instructor.setFirstName(instructorRequest.firstName());
        instructor.setLastName(instructorRequest.lastName());
        instructor.setPhoneNumber(instructorRequest.phoneNumber());
        instructor.setSpecialAction(instructorRequest.specialAction());
        instructorRepository.save(instructor);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Instructor successfully update ☺")
                .build();
    }

    @Override
    public SimpleResponse deleteInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Instructor" + "with id:" + id + " not found !!!"));
        if (instructor != null) {
            User user = instructor.getUser();
            if (user != null) {
                user.setInstructor(null);
            }
            instructorRepository.deleteById(id);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Instructor with id:" + id + " successfully deleted ☺")
                    .build();
        } else {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Instructor with id:" + id + " not found !!!")
                    .build();
        }
    }

    @Override
    public CounterStudentByGroup counterStudentsByInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Instructor with id:" + id + " not found !!!"));
        List<Student> students = new ArrayList<>();
        List<Course> courses = instructor.getCourses();
        for (Course c : courses) {
            for (Group g : c.getGroups()) {
                students.addAll(g.getStudents());
            }
        }
        int count = students.size();
        return CounterStudentByGroup.builder()
                .counter(count)
                .description(instructor.getFirstName() + " агайдын(эжекенин) IDси:" + id + " томонкучо окуучулары бар.")
                .build();
    }

    @Override
    public SimpleResponse assignInstructorToCourse(Long courseId, Long id) {
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new NotFoundException("Course with id:" + courseId + " not found !!!"));
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Instructor with id:" + id + " not found !!!"));
        course.setInstructor(instructor);
        instructor.setCourses(courses);
        instructorRepository.save(instructor);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Instructor with id:" + id + " successfully assign to Course with id:" + courseId)
                .build();
    }

    @Override
    public AboutInstructor aboutAllInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Instructor with id:" + id + " not found !!!"));
        List<Student> students = getAllStudentsByInstructorId(id);
        Map<String, Integer> countSt = new HashMap<>();
        Map<String, String> studentsByInstructor = new HashMap<>();
        for (Student s : students) {
            countSt.put("Count students:", students.size());
            studentsByInstructor.put(s.getFirstName(), s.getPhoneNumber());
        }
        return AboutInstructor.builder()
                .firstName(instructor.getFirstName())
                .lastName(instructor.getLastName())
                .phoneNumber(instructor.getPhoneNumber())
                .specialAction(instructor.getSpecialAction())
                .students(studentsByInstructor)
                .counter(countSt)
                .build();
    }

    @Override
    public List<Student> getAllStudentsByInstructorId(Long id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Instructor with id:" + id + " not found !!!"));
        return instructor.getCourses().stream().flatMap(course -> course.getGroups().stream())
                .flatMap(group -> group.getStudents().stream()).collect(Collectors.toList());
    }

    @Override
    public SimpleResponse deleteInstructorSimple(Long id) {
        instructorRepository.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Instructor with id:" + id + " successfully deleted ☺")
                .build();
    }

    @Override
    public  SimpleResponse saveInstructor(InstructorRequest instructorRequest) {
        Instructor instructor=new Instructor();
        instructor.setFirstName(instructorRequest.firstName());
        instructor.setLastName(instructorRequest.lastName());
        instructor.setEmail(instructorRequest.email());
        instructor.setPhoneNumber(instructorRequest.phoneNumber());
        String password=instructorRequest.password();
        if (instructorRequest.password()!=null){
            instructor.setPassword(passwordEncoder.encode(password));
        }
        instructor.setSpecialAction(instructorRequest.specialAction());
        instructor.setRole(instructorRequest.role());
        instructorRepository.save(instructor);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Instructor successfully update ☺")
                .build();
    }

    @Override
    public SimpleResponse assignInstructorToCompany(Long companyId, Long id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Instructor with id:" + id + " not found !!!"));
        Company company = companyRepository.findById(companyId).orElseThrow(() ->
                new NotFoundException("Company with id:" + companyId + " not found !!!"));
        company.getInstructors().add(instructor);
        instructor.getCompanies().add(company);
        instructorRepository.save(instructor);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Instructor with Id:"+id+" successfully assign  ☺")
                .build();


    }
}
