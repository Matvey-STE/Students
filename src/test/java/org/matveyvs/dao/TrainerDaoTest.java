package org.matveyvs.dao;

import lombok.Cleanup;
import org.junit.jupiter.api.Test;
import org.matveyvs.entity.Course;
import org.matveyvs.entity.Trainer;
import org.matveyvs.utils.HibernateUtil;

import java.util.List;

class TrainerDaoTest {
    private final TrainerDao trainerDao = TrainerDao.getInstance();
    private final CourseDao courseDao = CourseDao.getInstance();

    @Test
    void checkManyToMany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        Trainer trainer = session.get(Trainer.class, 2);
        Course course = session.get(Course.class, 12);
        trainer.addCourse(course);
        session.save(trainer);
        session.getTransaction().commit();
    }
//8. Добавить тест, сохраняющий нового тренера и список курсов, которые он ведет.
    @Test
    void createNewTrainer() {
        List<String> courseList = List.of("Basic", "Java Enterprise");
        List<Course> listOfCourseBy = courseDao.getListOfCourseBy(courseList);
        trainerDao.createNewTrainer("Angelina", listOfCourseBy);
    }
}