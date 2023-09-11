package peaksoft.dto.pagination;
import lombok.*;
import peaksoft.dto.response.CourseResponse;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoursePagination {
    private List<CourseResponse>courses;
    private int currentPage;
    private int pageSize;
}
