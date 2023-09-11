package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    @SequenceGenerator(name = "course_seq",allocationSize = 1)
    private Long id;
    @Column(name = "course_name",unique = true)
    private String courseName;
    @Column(name = "date_of_start")
    private LocalDate dateOfStart;
    private String description;
    @ManyToOne(cascade = {PERSIST,DETACH,MERGE,REFRESH})
    private Company company;
    @ManyToMany(mappedBy = "courses",cascade = {PERSIST,DETACH,MERGE,REFRESH,REMOVE})
    private List<Group> groups;
    @OneToMany(mappedBy = "course",cascade = {PERSIST,DETACH,MERGE,REFRESH})
    private List<Lesson>lessons;
    @ManyToOne(cascade ={PERSIST,DETACH,MERGE,REFRESH} )
    private Instructor instructor;

}