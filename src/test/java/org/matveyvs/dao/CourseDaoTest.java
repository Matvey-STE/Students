package org.matveyvs.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseDaoTest {
    private static final String javaEnterpriseStudents = "Java Enterprise";
    private final CourseDao courseDao = CourseDao.getInstance();
//6. Удалить курс "Java Enterprise"6. Удалить курс "Java Enterprise"
    @Test
    void removeCourseByCourseName() {
        boolean result = courseDao.removeCourseByCourseName(javaEnterpriseStudents);
        assertTrue(result);
    }

    @Test
    void changeNameOfCourse() {
        boolean result = courseDao.changeNameOfCourse(javaEnterpriseStudents, "Angular");
        assertTrue(result);
    }
}