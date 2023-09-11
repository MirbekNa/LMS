package peaksoft.service;

import peaksoft.dto.request.GroupRequest;
import peaksoft.dto.response.CounterStudentByGroup;
import peaksoft.dto.response.GroupResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface GroupService {
    List<GroupResponse> getAllGroups();

    SimpleResponse saveGroupWithCourse(Long courseId, GroupRequest groupRequest);

    GroupResponse findGroupById(Long id);

    SimpleResponse updateGroupWithCourse(Long courseId, Long id, GroupRequest groupRequest);

    SimpleResponse deleteGroup(Long id);

    CounterStudentByGroup counterStudentsByGroup(Long groupId);

    SimpleResponse saveGroup(GroupRequest groupRequest);
}
