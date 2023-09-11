package peaksoft.service;

import peaksoft.dto.request.InstructorRequest;
import peaksoft.dto.response.AboutInstructor;
import peaksoft.dto.response.CounterStudentByGroup;
import peaksoft.dto.response.InstructorResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Student;

import java.util.List;

public interface InstructorService {
    List<InstructorResponse> getAllInstructors();


    InstructorResponse findInstructorById(Long id);

    SimpleResponse updateInstructor(Long id, InstructorRequest instructorRequest);

    SimpleResponse deleteInstructor(Long id);

    SimpleResponse saveInstructorToCompany(Long companyId, InstructorRequest instructorRequest);

    CounterStudentByGroup counterStudentsByInstructor(Long id);

    SimpleResponse assignInstructorToCourse(Long courseId, Long id);

    AboutInstructor aboutAllInstructor(Long id);
    List<Student>getAllStudentsByInstructorId(Long id);

    SimpleResponse deleteInstructorSimple(Long id);

    SimpleResponse saveInstructor(InstructorRequest instructorRequest);

    SimpleResponse assignInstructorToCompany(Long companyId, Long id);
}
