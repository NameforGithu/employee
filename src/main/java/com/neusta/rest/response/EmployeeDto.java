package com.neusta.rest.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private Long id;
    @NotNull
    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;
    @NotNull
    @Column(name = "lastname", nullable = false, length = 30)
    private String lastname;
    @NotNull
    @Column(name = "username", nullable = false, length = 30)
    private String username;
    @NotNull
    @Column(name = "email", nullable = false, length = 30)
    private String email;

    @Column(name = "mobile", nullable = false, length = 30)
    private String mobile;
}
