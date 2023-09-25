package org.matveyvs.dao;

import lombok.Cleanup;
import org.matveyvs.entity.Course;
import org.matveyvs.entity.Trainer;
import org.matveyvs.utils.HibernateUtil;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class TrainerDao {

    private static final TrainerDao INSTANCE = new TrainerDao();
//8. Добавить тест, сохраняющий нового тренера и список курсов, которые он ведет.
    public void createNewTrainer(String trainerName, List<Course> courseList) {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        Trainer trainer = Trainer.builder().name(trainerName).build();
        for (Course course : courseList) {
            trainer.addCourse(course);
            session.save(trainer);
        }
        session.getTransaction().commit();
    }


    private TrainerDao() {

    }

    public static TrainerDao getInstance() {
        return INSTANCE;
    }
}
