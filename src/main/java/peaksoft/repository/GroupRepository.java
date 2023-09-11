package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.GroupResponse;
import peaksoft.entity.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("select new peaksoft.dto.response.GroupResponse(g.groupName,g.imageLink,g.description) from Group g")
    List<GroupResponse> findAllGroups();

    GroupResponse findGroupById(Long id);
}
