package com.neusta.rest.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neusta.domain.Framework;
import com.neusta.domain.ProgrammingLanguage;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    @Column(name = "email", nullable = false, length = 30)
    private String email;

    @Column(name = "mobile", nullable = false, length = 30)
    private String mobile;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime creationTime;

    @Column(name = "programming_languages")
    List<ProgrammingLanguage> programmingLanguages;
    @Column(name = "framework")
    List<Framework> frameworks;

    @Column(name = "employee_status")
    private boolean isFree = true;

    public boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(boolean free) {
        isFree = free;
    }

}
