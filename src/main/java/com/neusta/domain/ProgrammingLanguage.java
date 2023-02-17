package com.neusta.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "programming_language")
@AllArgsConstructor
@NoArgsConstructor
public class ProgrammingLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "language_id", nullable = false)
    private Long id;

    @Column(name = "programming_language")
    private String programmingLanguage;
    private int workExperience;

    public ProgrammingLanguage(String programming_language, int amountOfExperience) {
        this.programmingLanguage = programming_language;
        this.workExperience = amountOfExperience;
    }
}
