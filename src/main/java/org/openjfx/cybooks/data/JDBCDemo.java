package org.openjfx.cybooks.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class JDBCDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost/CY-Books";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from books");

            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " " + resultSet.getString("title"));
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
