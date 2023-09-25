package org.matveyvs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "grade_info")
public class GradeInfo {
    @Id
    @Column(name = "student_id")
    private Integer id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Student student;
    private Double grade;

    public void setStudent(Student student) {
        this.student = student;
        student.setGradeInfo(this);
        id = student.getId();
    }
}
//3. Добавить таблицу student_profile с информацией об успеваемости