package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import peaksoft.dto.request.StudentRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StudentResponse;
import peaksoft.entity.Group;
import peaksoft.entity.Student;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.GroupRepository;
import peaksoft.repository.StudentRepository;
import peaksoft.service.StudentService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAllStudents();
    }


    @Override
    public SimpleResponse saveStudentToGroup(Long groupId, StudentRequest studentRequest) {
        Group group = groupRepository.findById(groupId).orElseThrow(() ->
                new NotFoundException("Group with id:" + groupId + " not found!!!"));
        Student student = new Student();
        student.setFirstName(studentRequest.firstName());
        student.setLastName(studentRequest.lastName());
        student.setEmail(studentRequest.email());
        student.setPhoneNumber(studentRequest.phoneNumber());

        String password = studentRequest.password();
        if (studentRequest.password() != null) {
            student.setPassword(passwordEncoder.encode(password));
        } else {
            throw new NotFoundException("Wrong password or not found password !!!");
        }
        student.setStudyFormat(studentRequest.studyFormat());
        student.setIsBlocked(studentRequest.isBlocked());
        student.setRole(studentRequest.role());
        group.setStudents(List.of(student));
        student.setGroup(group);
        studentRepository.save(student);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Student with id:" + student.getId() + " successfully assigned to Group with id:" + groupId)
                .build();
    }

    @Override
    public SimpleResponse updateStudent(Long id, StudentRequest studentRequest) {
        Student student = studentRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Student with id:" + id + " not found!!!"));
        student.setFirstName(studentRequest.firstName());
        student.setLastName(studentRequest.lastName());
        student.setPhoneNumber(studentRequest.phoneNumber());
        student.setEmail(studentRequest.email());
        student.setStudyFormat(studentRequest.studyFormat());
        studentRepository.save(student);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Student with id:" + id + " successfully updated ☺")
                .build();
    }

    @Override
    public SimpleResponse deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Student with id:" + id + " not found !!!"));
        studentRepository.delete(student);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Student with id:" + id + " successfully deleted ☺")
                .build();
    }

    @Override
    public SimpleResponse isBlockedStudent(Long id, Map<String, Object> fields) {
        Student student = studentRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Student with id:" + id + " not found !!!"));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Student.class, key);
            if (field != null) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                if (fieldType.isEnum() && value instanceof String) {
//                     Convert the string value to the enum type
                    Enum<?> enumValue = Enum.valueOf((Class<Enum>) fieldType, (String) value);
                    ReflectionUtils.setField(field, student, enumValue);
                } else if (fieldType == String.class && value instanceof String) {
                    ReflectionUtils.setField(field, student, value);
                }
            }
        });

        studentRepository.save(student);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Student with id:" + student.getId() + " successfully blocked")
                .build();
    }

    @Override
    public SimpleResponse save(StudentRequest studentRequest) {
        Student student = new Student();
        student.setFirstName(studentRequest.firstName());
        student.setLastName(studentRequest.lastName());
        student.setEmail(studentRequest.email());
        student.setPhoneNumber(studentRequest.phoneNumber());
        String password = studentRequest.password();
        if (password != null) {
            student.setPassword(passwordEncoder.encode(password));
        } else {
            throw new NotFoundException("Password not !!!");
        }
        student.setIsBlocked(studentRequest.isBlocked());
        student.setStudyFormat(studentRequest.studyFormat());
        student.setRole(studentRequest.role());
        studentRepository.save(student);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Student with id:" + student.getId() + " successfully saved !!!")
                .build();
    }

    @Override
    public SimpleResponse assignStudentToGroup(Long studentId, Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() ->
                new NotFoundException("Group with id:" + groupId + " not found!!"));
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new NotFoundException("Student with id :" + studentId + " not found !!"));
//        group.setStudents(List.of(student));
        student.setGroup(group);
        studentRepository.save(student);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Student with id:" + student.getId() + " successfully saved !!!")
                .build();
    }
}