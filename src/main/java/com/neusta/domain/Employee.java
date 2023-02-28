package com.neusta.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;
    @Column(name = "lastname", nullable = false, length = 30)
    private String lastname;
    @Column(name = "username", unique = true, nullable = false, length = 30)
    private String username;
    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "email", unique = true, nullable = false, length = 30)
    private String email;
    @Column(name = "mobile", unique = true, nullable = false, length = 30)
    private String mobile;
    @Size(min = 1, max = 5, message = "Rating must be between 1 and 5")
    private int troubleshooting;
    @Size(min = 1, max = 5, message = "Rating must be between 1 and 5")
    private int flexibility;
    @Size(min = 1, max = 5, message = "Rating must be between 1 and 5")
    private int creativity;
    @Size(min = 1, max = 5, message = "Rating must be between 1 and 5")
    private int teamwork;
    @OneToMany(cascade = CascadeType.ALL, fetch =  FetchType.LAZY)
    private List <ForeignLanguages> foreignLanguages;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "employee_programming_languages",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "programming_language_id"))
    List<ProgrammingLanguage> programmingLanguages = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable (name = "employee_frameworks",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "framework_id"))
    List<Framework> frameworks = new ArrayList<>();

    @Column(name = "creation_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime creationTime;

    @Column(name = "employee_status")
    private Boolean isFree = true;
}


