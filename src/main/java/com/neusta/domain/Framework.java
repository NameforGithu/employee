package com.neusta.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "frameworks")
public class Framework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "framework_id", nullable = false)
    private Long id;
    @Column(name = "framework")
    private String framework;
    @Column(name = "work_experience")
    private int workExperience;
    public Framework(String framework, int workExperience) {
        this.framework = framework;
        this.workExperience = workExperience;
    }
}
