package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.InstructorRequest;
import peaksoft.dto.response.AboutInstructor;
import peaksoft.dto.response.CounterStudentByGroup;
import peaksoft.dto.response.InstructorResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.InstructorService;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
@RequiredArgsConstructor
public class InstructorApi {
    private final InstructorService instructorService;

    @PermitAll
    @GetMapping
    List<InstructorResponse> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    SimpleResponse saveInstructor(@RequestBody InstructorRequest instructorRequest) {
        return instructorService.saveInstructor(instructorRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save/companyId/{companyId}")
    SimpleResponse saveInstructorToCompany(@PathVariable Long companyId,
                                           @RequestBody @Valid InstructorRequest instructorRequest) {
        return instructorService.saveInstructorToCompany(companyId, instructorRequest);
    }

    @PermitAll
    @GetMapping("/{id}")
    InstructorResponse getInstructorById(@PathVariable Long id) {
        return instructorService.findInstructorById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    SimpleResponse updateInstructor(@PathVariable Long id, InstructorRequest instructorRequest) {
        return instructorService.updateInstructor(id, instructorRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}")
    SimpleResponse deleteInstructor(@PathVariable Long id) {
        return instructorService.deleteInstructor(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/deleteSimple{id}")
    SimpleResponse deleteInstructorSimple(@PathVariable Long id) {
        return instructorService.deleteInstructorSimple(id);
    }


    @PermitAll
    @GetMapping("/{id}/students")
    CounterStudentByGroup counterStudentsByInstructor(@PathVariable Long id) {
        return instructorService.counterStudentsByInstructor(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/course{courseId}/assignToCourse/instructor/{id}")
    SimpleResponse assignInstructorToCourse(@PathVariable Long courseId,
                                            @PathVariable Long id) {
        return instructorService.assignInstructorToCourse(courseId, id);
    }

    @PermitAll
    @GetMapping("/aboutInstructor/{id}")
    AboutInstructor aboutAllInstructor(@PathVariable Long id) {
        return instructorService.aboutAllInstructor(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{companyId}/assign/{id}")
    SimpleResponse assignInstructorToCompany(@PathVariable Long companyId,
                                             @PathVariable Long id) {
        return instructorService.assignInstructorToCompany(companyId, id);
    }

}
