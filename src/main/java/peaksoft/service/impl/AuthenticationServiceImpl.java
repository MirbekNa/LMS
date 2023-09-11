package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Instructor;
import peaksoft.entity.Student;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.AlreadyExistException;
import peaksoft.exceptions.BadCredentialException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.InstructorRepository;
import peaksoft.repository.StudentRepository;
import peaksoft.repository.UserRepository;
import peaksoft.security.jwt.JwtService;
import peaksoft.service.AuthenticationService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Override
    public void init() {
        User user = new User();
        user.setRole(Role.ADMIN);
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin"));
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            userRepository.save(user);
        }
    }

    @Override
    public SimpleResponse signIn2(SignRequest signRequest) {
        if (signRequest.role().equals(Role.INSTRUCTOR)) {
            Instructor instructor = convertRequestToInstructor(signRequest);
            User user = convertUser(signRequest);
            instructor.setUser(user);
            user.setInstructor(instructor);
            userRepository.save(user);
            instructorRepository.save(instructor);
            log.info("Successfully saved Instructor with id:" + instructor.getId());
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CREATED)
                    .message("Instructor successfully saved with id:" + instructor.getId())
                    .build();
        } else if (signRequest.role().equals(Role.STUDENT)) {
            Student student = convertRequestToStudent(signRequest);
            User user = convertUser(signRequest);
            student.setUser(user);
            user.setStudent(student);
            studentRepository.save(student);
            userRepository.save(user);
            log.info("Successfully saved student with id:" + student.getId());
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CREATED)
                    .message("Student with id:" + student.getId() + " successfully saved ☺")
                    .build();
        } else {
            log.info("Role is invalid!!!");
            throw new NotFoundException(" Role is invalid !!!");
        }
    }

    private Student convertRequestToStudent(SignRequest signRequest) {

        Student student = new Student();
        student.setFirstName(signRequest.firstName());
        student.setLastName(signRequest.lastName());
        student.setEmail(signRequest.email());
        student.setPhoneNumber(signRequest.phoneNumber());
        student.setPassword(passwordEncoder.encode(signRequest.password()));
        student.setIsBlocked(signRequest.isBlocked());
        student.setStudyFormat(signRequest.studyFormat());
        student.setRole(signRequest.role());
        return student;
    }

    private Instructor convertRequestToInstructor(SignRequest signRequest) {
        Instructor instructor = new Instructor();
        instructor.setFirstName(signRequest.firstName());
        instructor.setLastName(signRequest.lastName());
        instructor.setEmail(signRequest.email());
        instructor.setPhoneNumber(signRequest.phoneNumber());
        instructor.setPassword(passwordEncoder.encode(signRequest.password()));
        instructor.setSpecialAction(signRequest.specialAction());
        instructor.setRole(signRequest.role());
        return instructor;
    }

    private User convertUser(SignRequest signRequest) {
        User user = new User();
        user.setEmail(signRequest.email());
        user.setPassword(passwordEncoder.encode(signRequest.password()));
        user.setRole(signRequest.role());
        return user;
    }

    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) throws AlreadyExistException {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new AlreadyExistException(
                    "User with email:" + signUpRequest.email() + " already exists !"
            );
        }
        User user = User.builder()
                .email(signUpRequest.email())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .role(signUpRequest.role())
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) throws BadCredentialException {
        User user = userRepository.getUserByEmail(signInRequest.email()).orElseThrow(() -> new NotFoundException("" +
                "User with email:" + signInRequest.email() + " not found !!!"));
        if (signInRequest.email().isBlank()) {
            throw new BadCredentialException("Email is blank");
        }
        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            throw new BadCredentialException("Wrong password !!!");
        }
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public SimpleResponse deleteUserById(Long id) {
        userRepository.deleteById(id);
        return SimpleResponse.builder()
                .message("User with id:" + id + " successfully deleted!!!")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse deleteUserByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            userRepository.deleteUserByEmail(email);
        } else {
            return SimpleResponse.builder()
                    .message("User with id:" + email + " not found !!!")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
        return SimpleResponse.builder()
                .message("User with id:" + email + " successfully deleted!!!")
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
