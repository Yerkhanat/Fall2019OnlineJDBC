package com.jdbc.day1;

import java.sql.*;

public class InsertAndDelete {
    static String URL = "jdbc:oracle:thin:@18.234.240.176:1521:xe";
    static String username = "hr";
    static String password = "hr";


    public static void main(String[] args) throws SQLException {


        //to establish connection with a database
        Connection connection = DriverManager.getConnection(URL, username, password);
        Statement statement= connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String INSERT_QUERY ="INSERT INTO employees VALUES(225,'Yerkhanat', 'Myedyeukhan', 'yerqa@cybertek.com', '714-400-1624', SYSDATE, 'SDET', 19999,0,NULL,NULL)";
        String DELETE_QUERY="DELETE FROM employees WHERE employee_id =225";
        ResultSet resultSet =statement.executeQuery(INSERT_QUERY);


        resultSet.close();
        statement.close();
        connection.close();



    }
}
