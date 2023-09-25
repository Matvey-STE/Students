package org.matveyvs.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@ToString(exclude = {"course", "gradeInfo"})
@AllArgsConstructor
@Builder
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToOne
    @JoinColumn (name = "course_id")
    private Course course;
    @OneToOne(mappedBy = "student")
    private GradeInfo gradeInfo;
}
