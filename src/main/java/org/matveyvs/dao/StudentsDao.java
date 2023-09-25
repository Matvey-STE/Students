package org.matveyvs.dao;

import lombok.Cleanup;
import org.matveyvs.entity.Course;
import org.matveyvs.entity.GradeInfo;
import org.matveyvs.entity.Student;
import org.matveyvs.utils.HibernateUtil;

import javax.persistence.NoResultException;
import java.util.List;

public class StudentsDao {
    private static final StudentsDao INSTANCE = new StudentsDao();
//    5. Добавить студента к курсу "Java Enterprise"
    public Student addNewStudentInDatabase(Student student, GradeInfo gradeInfo, Course course) {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Course existingCourse = session
                    .createQuery("select c from Course c where c.name = :name", Course.class)
                    .setParameter("name", course.getName())
                    .getSingleResult();

            student.setCourse(existingCourse);
        } catch (NoResultException ex) {
            session.save(course);
            student.setCourse(course);
        }
        session.save(student);
        gradeInfo.setStudent(student);
        session.save(gradeInfo);
        session.getTransaction().commit();
        return student;
    }

    public List<Student> findAllStudents(String courseName) {
        List<Student> students = null;
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        students = session
                .createQuery("select s from Student s join s.course c where c.name = :name", Student.class)
                .setParameter("name", courseName)
                .list();
        session.getTransaction().commit();
        return students;
    }
//4. Удалить всех студентов на курсе "Java Enterprise" со средней оценкой ниже 6
    public Integer removeAllStudentsWithAverageGrade(String courseName, Double averageGrade) {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        int counter = 0;
        List<Student> allStudents = findAllStudents(courseName);
        session.beginTransaction();
        for (Student student : allStudents) {
            if (student.getGradeInfo().getGrade() < averageGrade) {
                session.delete(student.getGradeInfo());
                session.delete(student);
                counter++;
            }
        }
        session.getTransaction().commit();
        return counter;
    }

    private StudentsDao() {

    }

    public static StudentsDao getInstance() {
        return INSTANCE;
    }
}
