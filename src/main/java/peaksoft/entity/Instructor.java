package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.enums.Role;
import peaksoft.enums.SpecialAction;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "instructors")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "instructor_seq")
    @SequenceGenerator(name = "instructor_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    private String password;
    @Enumerated(EnumType.STRING)
    private SpecialAction specialAction;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "instructor",cascade = ALL)
    private User user;
    @ManyToMany(cascade = {PERSIST, DETACH, REFRESH, MERGE})
    private List<Company> companies;
    @OneToMany(mappedBy = "instructor", cascade = {PERSIST, DETACH, REFRESH, MERGE})
    private List<Course> courses;

}