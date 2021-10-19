package com.example.jwd.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/elective";
    public static final String USER = "root";
    public static final String PASSWORD = "13041993Sofia";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static final String SQL_SELECT = "SELECT * FROM teacher";
    public static final String SQL_INSERT = "INSERT INTO teacher( teacher_id, first_name, last_name, login, password) values (4, 'Краморов', 'Савелий', 'SKramorov3', '1111')";

    public static void main(String[] args) {

        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER,
                            PASSWORD);
            System.out.println(("Соединение установлено."));

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT);

            statement = connection.createStatement();
            int count = 0;
                 count = statement.executeUpdate(SQL_INSERT);

            System.out.println("Count : " + count);

            while (resultSet.next()){
                System.out.println(resultSet.getInt(1) + " " +
                        resultSet.getString(2)+ " "+
                        resultSet.getString(3)+ " "+
                        resultSet.getString(4)+ " "+
                        resultSet.getString( 5) + " ");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

}