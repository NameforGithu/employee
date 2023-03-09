package com.neusta.rest.response;

import com.neusta.domain.ForeignLanguages;
import com.neusta.domain.Framework;
import com.neusta.domain.ProgrammingLanguage;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

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
    @Email(message = "invalid email")
    @Column(name = "email", nullable = false, length = 30)
    private String email;
    @Column(name = "mobile", nullable = false, length = 30)
    @Pattern(regexp="(^$|[0-9]{10})")
    private String mobile;
    @Column(name = "programming_languages")
    List<ProgrammingLanguage> programmingLanguages;
    @Column(name = "framework")
    List<Framework> frameworks;
    @Size(min = 1, max = 5, message = "Rating must be between 1 and 5")
    private int troubleshooting;
    @Size(min = 1, max = 5, message = "Rating must be between 1 and 5")
    private int flexibility;
    @Size(min = 1, max = 5, message = "Rating must be between 1 and 5")
    private int creativity;
    @Size(min = 1, max = 5, message = "Rating must be between 1 and 5")
    private int teamwork;
    private List <ForeignLanguages> foreignLanguages;
    @Column(name = "employee_status")
    private boolean isFree = true;
    public boolean getIsFree() {
        return isFree;
    }
    public void setIsFree(boolean free) {isFree = free;}
}
