package org.matveyvs.dao;

import lombok.Cleanup;
import org.matveyvs.entity.Course;
import org.matveyvs.utils.HibernateUtil;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    private static final CourseDao INSTANCE = new CourseDao();
//      6. Удалить курс "Java Enterprise"
//    10. Добавить тест для удаления курса.
    public boolean removeCourseByCourseName(String courseName) {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        int result = session
                .createQuery("delete from Course c where c.name = :name")
                .setParameter("name", courseName)
                .executeUpdate();
        session.getTransaction().commit();
        return result > 0;
    }
//9. Добавить тест, для изменения курса.
    public boolean changeNameOfCourse(String name, String newName) {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Course course = session
                    .createQuery("select c from Course c where c.name = :name", Course.class)
                    .setParameter("name", name)
                    .getSingleResult();
            course.setName(newName);
            session.save(course);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Course> getListOfCourseBy(List<String> coursesNames) {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        List<Course> list = new ArrayList<>();
        for (String courseName : coursesNames) {
            try {
                Course existingCourse = session
                        .createQuery("select c from Course c where c.name = :name", Course.class)
                        .setParameter("name", courseName)
                        .getSingleResult();
                list.add(existingCourse);
            } catch (NoResultException ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    private CourseDao() {

    }

    public static CourseDao getInstance() {
        return INSTANCE;
    }
}
