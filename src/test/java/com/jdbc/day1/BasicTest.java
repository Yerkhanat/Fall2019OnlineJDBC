package com.jdbc.day1;

import java.sql.*;

public class BasicTest {

    public static void main(String[] args) throws SQLException {
        String URL = "jdbc:oracle:thin:@18.234.240.176:1521:xe";
        String username = "hr";
        String password = "hr";

        //to establish connection with a database
        Connection connection = DriverManager.getConnection(URL, username, password);
        Statement statement= connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet resultSet = statement.executeQuery("SELECT * FROM employees Order By 1");


//        while (resultSet.next()){
//
//            System.out.println(resultSet.getString(1)+" "+resultSet.getString(2)+" "+resultSet.getString(3));
//
//
//        }
        resultSet.beforeFirst(); //to comeback to the beginning of result set

        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSetMetaData resultSetMetaData =resultSet.getMetaData();
        System.out.println("JDBC Driver "+ databaseMetaData.getDriverName());
        System.out.println("JDBC Driver version "+ databaseMetaData.getDriverVersion());
        System.out.println("Database name "+databaseMetaData.getDatabaseProductName());
        System.out.println("Database version "+databaseMetaData.getDatabaseProductVersion());

        System.out.println("Number of column: "+ resultSetMetaData.getColumnCount());
        System.out.println("label of 1st column: "+ resultSetMetaData.getColumnName(1));
        System.out.println("Data type of first name: "+resultSetMetaData.getColumnTypeName(1));

        System.out.println("###############################");
        for (int columnIndex = 1; columnIndex <resultSetMetaData.getColumnCount() ; columnIndex++) {
            System.out.printf("%-20s",resultSetMetaData.getColumnName(columnIndex));

        }

        System.out.println(" ");
        while (resultSet.next()){
//            System.out.println(resultSet.getString("salary"));

            for (int columnIndex = 1; columnIndex <resultSetMetaData.getColumnCount() ; columnIndex++) {

                System.out.printf("%-20s",resultSet.getString(columnIndex)+ " ");

            }

            System.out.println(" ");

        }

        resultSet.close();
        statement.close();
        connection.close();





    }
}
