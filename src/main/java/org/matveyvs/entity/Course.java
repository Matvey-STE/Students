package org.matveyvs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @OneToMany(mappedBy = "course")
    private List<Student> students;
    @Builder.Default
    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    private List<Trainer> trainers = new ArrayList<>();
}
