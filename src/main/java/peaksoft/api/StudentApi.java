package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.StudentRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StudentResponse;
import peaksoft.service.StudentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentApi {
    private final StudentService studentService;

    @PermitAll
    @GetMapping()
    List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }


//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    @PostMapping("/{groupId}")
//    SimpleResponse saveStudentToGroup(@PathVariable Long groupId,
//                                      @RequestBody StudentRequest studentRequest) {
//        return studentService.saveStudentToGroup(groupId, studentRequest);
//    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{groupId}/assign/{studentId}")
    SimpleResponse assignStudentToGroup(@PathVariable Long studentId, @PathVariable Long groupId) {
        return studentService.assignStudentToGroup(studentId, groupId);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    SimpleResponse updateStudent(@PathVariable Long id,
                                 @RequestBody StudentRequest studentRequest) {
        return studentService.updateStudent(id, studentRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}")
    SimpleResponse deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PostMapping("isBlocked/{id}")
    SimpleResponse isBlockedStudent(@PathVariable Long id,
                                    @RequestBody Map<String, Object> fields) {
        return studentService.isBlockedStudent(id, fields);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PostMapping("/save")
    SimpleResponse saveStudent(@RequestBody StudentRequest studentRequest) {
        return studentService.save(studentRequest);
    }

}
