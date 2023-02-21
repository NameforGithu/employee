package com.neusta.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "email", unique = true, nullable = false, length = 30)
    private String email;
    @Column(name = "mobile", unique = true, nullable = false, length = 30)
    private String mobile;

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


