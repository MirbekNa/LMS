package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_seq")
    @SequenceGenerator(name = "lesson_seq", allocationSize = 1)
    private Long id;
    @Column(name = "lesson_name")
    private String lessonName;
    @ManyToOne(cascade = {PERSIST, MERGE, DETACH, REFRESH})
    private Course course;
    @OneToMany(mappedBy = "lesson", cascade = {ALL})
    private List<Task> tasks;

}