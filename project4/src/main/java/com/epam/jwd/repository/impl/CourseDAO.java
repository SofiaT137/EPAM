package com.epam.jwd.repository.impl;

public class CourseDAO {

    private static final String SQL_SAVE_COURSE = "INSERT INTO course (course_id, name, start_date, end_date) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_COURSE = "SELECT * FROM course";
    private static final String SQL_FIND_COURSE_BY_ID = "SELECT * FROM course WHERE course_id =  ?";
    private static final String SQL_UPDATE_PAYMENT_BY_ID = "UPDATE payments SET user_id = ?, destination_address = ?, price = ?, committed = ?, time = ?, name = ? WHERE id = ?";
    private static final String SQL_DELETE_PAYMENT_BY_ID = "DELETE FROM payments WHERE id = ?";
}
