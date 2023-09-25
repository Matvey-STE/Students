package org.matveyvs.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.matveyvs.entity.Course;
import org.matveyvs.entity.GradeInfo;
import org.matveyvs.entity.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentsDaoTest {
    private static final String javaEnterpriseStudents = "Java Enterprise";
    private static final Double removeWithAverageGrade = 6.0;
    StudentsDao studentsDao = StudentsDao.getInstance();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
//    2. Найти всех студентов которые учаться на курсе "Java Enterprise"
    @Test
    void findAllStudents() {
        List<Student> allStudentsJavaEnterprise =
                studentsDao.findAllStudents(javaEnterpriseStudents);
        System.out.println(allStudentsJavaEnterprise);
        for (Student student : allStudentsJavaEnterprise) {
            assertEquals(javaEnterpriseStudents, student.getCourse().getName());
        }
    }
//4. Удалить всех студентов на курсе "Java Enterprise" со средней оценкой ниже 6
    @Test
    void removeAllStudentsWithAverageGrade() {
        List<Student> allStudentsJavaEnterprise =
                studentsDao.findAllStudents(javaEnterpriseStudents);
        long count = allStudentsJavaEnterprise.stream()
                .filter(student -> student.getGradeInfo().getGrade() < removeWithAverageGrade)
                .count();
        System.out.println(count);
        Integer integer = studentsDao
                .removeAllStudentsWithAverageGrade(javaEnterpriseStudents, removeWithAverageGrade);
        assertEquals((int) count, integer);
        List<Student> databaseResult =
                studentsDao.findAllStudents(javaEnterpriseStudents);
        for (Student student : databaseResult) {
            assertTrue(student.getGradeInfo().getGrade() > removeWithAverageGrade);
        }
    }

//    5. Добавить студента к курсу "Java Enterprise"
    @Test
    void addNewStudentInDatabase() {
        createNewUser(javaEnterpriseStudents, "Konstantin", "Vasiliev",
                4.9);
    }

    private void createNewUser(String nameOfCourse, String firstName, String lastName,
                               Double studentGrade) {
        Course course = Course.builder()
                .name(nameOfCourse).build();
        Student student = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .course(course)
                .build();
        GradeInfo gradeInfo = GradeInfo.builder()
                .grade(studentGrade)
                .build();
        studentsDao.addNewStudentInDatabase(student, gradeInfo, course);
    }
}