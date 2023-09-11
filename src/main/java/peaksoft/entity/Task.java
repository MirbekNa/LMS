package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq",allocationSize = 1)
    private Long id;
    @Column(name = "task_name")
    private String taskName;
    @Column(name = "taskText")
    private String taskText;
    @Column(name = "dead_line")
    private LocalDateTime deadLine;
    @ManyToOne(cascade = {PERSIST,DETACH,MERGE,REFRESH})
    private Lesson lesson;

}