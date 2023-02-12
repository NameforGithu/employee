package com.neusta.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}


