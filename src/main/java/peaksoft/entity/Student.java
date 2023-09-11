package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.enums.IsBlocked;
import peaksoft.enums.Role;
import peaksoft.enums.StudyFormat;

import static jakarta.persistence.CascadeType.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(name = "student_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "study_format")
    private StudyFormat studyFormat;
    @Enumerated(EnumType.STRING)
    @Column(name = "is_blocked")
    private IsBlocked isBlocked;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "student")
    private User user;
    @ManyToOne(cascade = {PERSIST, REFRESH, MERGE, DETACH})
    private Group group;

}