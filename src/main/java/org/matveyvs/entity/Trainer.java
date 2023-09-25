package org.matveyvs.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "trainer_course",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
        course.getTrainers().add(this);
    }
}
//7. Добавить таблицу Trainer, один тренер может вести несколько курсов,
// один курс могут проводить разные тренеры