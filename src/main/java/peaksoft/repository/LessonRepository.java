package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.LessonResponse;
import peaksoft.entity.Lesson;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long> {
@Query("select new peaksoft.dto.response.LessonResponse(l.id,l.lessonName) from Lesson l")
    List<LessonResponse> findAllLessons();
}
