package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.GroupRequest;
import peaksoft.dto.response.CounterStudentByGroup;
import peaksoft.dto.response.GroupResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupApi {
    private final GroupService groupService;

    @PermitAll
    @GetMapping
    List<GroupResponse> getAllGroups() {
        return groupService.getAllGroups();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/save")
    SimpleResponse saveGroup(@RequestBody GroupRequest groupRequest) {
        return groupService.saveGroup(groupRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save/{courseId}")
    SimpleResponse saveGroupWithCourse(@PathVariable Long courseId,
                                       @RequestBody GroupRequest groupRequest) {
        return groupService.saveGroupWithCourse(courseId, groupRequest);
    }

    @PermitAll
    @GetMapping("/{id}")
    GroupResponse findGroupById(@PathVariable Long id) {
        return groupService.findGroupById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{courseId}/update/{id}")
    SimpleResponse updateGroup(@PathVariable Long courseId,
                               @PathVariable Long id,
                               @RequestBody GroupRequest groupRequest) {
        return groupService.updateGroupWithCourse(courseId, id, groupRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}")
    SimpleResponse deleteGroup(@PathVariable Long id) {
        return groupService.deleteGroup(id);
    }

    @PermitAll
    @GetMapping("/{groupId}/students")
    CounterStudentByGroup counterStudentsByGroup(@PathVariable Long groupId) {
        return groupService.counterStudentsByGroup(groupId);
    }
}
