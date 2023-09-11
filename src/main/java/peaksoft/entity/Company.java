package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Country;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity
@Table(name = "companies")

public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
    @SequenceGenerator(name = "company_seq", allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private Country country;
    private String address;
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;
    @OneToMany(mappedBy = "company", cascade = ALL)
    private List<Course> courses;
    @ManyToMany(mappedBy = "companies", cascade = ALL)
    private List<Instructor> instructors;

    public Company(Long id, String name, Country country,
                   String address, String phoneNumber, List<Course> courses,
                   List<Instructor> instructors) {
        this.id = id;
        this.name=name;
        this.country = country;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.courses = courses;
        this.instructors = instructors;
    }

    public Company(String name, Country country, String address) {
        this.name = name;
        this.country = country;
        this.address = address;
    }

    public Company() {
    }
}