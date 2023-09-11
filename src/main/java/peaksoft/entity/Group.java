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
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
    @SequenceGenerator(name = "group_seq",allocationSize = 1)
    private Long id;
    @Column(name = "group_name")
    private String groupName;
    @Column(name ="image_link")
    private String imageLink;
    private String description;
    @ManyToMany(cascade = {PERSIST,DETACH,REFRESH,MERGE})
    private List<Course> courses;
    @OneToMany(mappedBy = "group",cascade = ALL)
    private List<Student>students;
}