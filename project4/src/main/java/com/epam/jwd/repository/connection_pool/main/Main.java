package com.epam.jwd.repository.connection_pool.main;


import com.epam.jwd.repository.impl.*;

import java.sql.Date;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws InterruptedException, SQLException {

//        RoleDAO roleDAO = new RoleDAO();
//        roleDAO.save(roleDAO.createRole("HEADMAN"));

//        AccountDAO accountDAO = new AccountDAO();
//        accountDAO.save(accountDAO.createAccount("HEADMAN","Headman5","1115"));

//        GroupDAO groupDAO = new GroupDAO();
//        groupDAO.save(groupDAO.createGroup("A"));

//         UserDAO userDAO = new UserDAO();
//         userDAO.save(userDAO.createUser("Headman3","A","Vasya","Yorimov"));

        CourseDAO courseDAO = new CourseDAO();
//        Date start_date = new Date(2021,8,14);
//        Date end_date = new Date(2021,10,14);
//        courseDAO.save(courseDAO.createCourse("Spanish",start_date,end_date));
//
        System.out.println(courseDAO.getUserAvailableCourses("Vasya","Pupkin"));

//        ReviewDAO reviewDAO = new ReviewDAO();
//        reviewDAO.save(reviewDAO.createReview("Vasya","Pupkin","Spanish",10,"Great result!"));



    }

}
