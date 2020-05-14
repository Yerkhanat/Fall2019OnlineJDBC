package com.jdbc.day2;

import com.github.javafaker.Faker;
import org.junit.Test;

import java.sql.*;
import java.util.*;

public class DatabaseTests {
    final String DB_URL ="jdbc:oracle:thin:@34.201.65.77:1521:xe";
    final String DB_USER = "hr";
    final String DB_PASSWORD = "hr";


    @Test
    public void getEmployeesData() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //ResultSet.TYPE_SCROLL_INSENSITIVE -that is scrollable and generally sensitive to changes to the data
        //ResultSet.CONCUR_READ_ONLY - creates resultSet that cannot be updated but can be read
        String QUERY = "SELECT * FROM employees";
        ResultSet resultSet =statement.executeQuery(QUERY);
        List<Integer> employeeID = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List< Map<String, Integer> > employeesIDsMap = new ArrayList<>();
        List< Map<String, String> > employeesNamesMap = new ArrayList<>();
        while (resultSet.next()){
            Map<String, Integer>map = new HashMap<>();
            map.put("employee_id",resultSet.getInt("employee_id"));
            employeesIDsMap.add(map);
            employeeID.add(resultSet.getInt("employee_id"));
            names.add(resultSet.getString("first_name")+" "+resultSet.getString("last_name"));
            String fullName = resultSet.getString("first_name")+" "+resultSet.getString("last_name");
            Map<String,String> name = new HashMap<>();
            name.put("full_name", fullName);
            employeesNamesMap.add(name);

        }
//        System.out.println(employeeID);
////        System.out.println(names);
//        System.out.println(employeesIDsMap);
//        System.out.println(employeesNamesMap);


        //get 5th employee name
        String fifthEmployee = employeesNamesMap.get(4).get("full_name");
        System.out.println("5th employee  - "+fifthEmployee);



        resultSet.close();
        statement.close();
        connection.close();




    }

    @Test
    public void insertTest() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        Statement statement= connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String QUERY_GET_LAST_EMPLOYEE_ID ="SELECT MAX(employee_id) FROM employees";
        ResultSet resultSet1 =statement.executeQuery(QUERY_GET_LAST_EMPLOYEE_ID);

        resultSet1.next();
        int employeeId =resultSet1.getInt(1)+1;

        boolean emailExists =false;
        String randomEmail = null;
        Faker faker = new Faker();
        do {

            randomEmail = faker.internet().emailAddress();
            String QUERY_TO_CHECK_IF_EMAIL_EXIST = "SELECT Count(*) FROM employees WHERE email ='"+randomEmail+"'";
           ResultSet resultSet2 = statement.executeQuery(QUERY_TO_CHECK_IF_EMAIL_EXIST);
            resultSet2.next();
            emailExists = resultSet2.getInt(1) > 0;
        }while (emailExists && randomEmail.length()<25);


        String QUERY ="INSERT INTO employees VALUES("+employeeId+",'"+faker.name().firstName()+"', '"+faker.name().lastName()+"', '"+randomEmail+"', '714-400-1624', SYSDATE, 'SDET', 19999,0,NULL,NULL)";
        System.out.println(QUERY);
        ResultSet resultSet =statement.executeQuery(QUERY);


        resultSet.close();
        statement.close();
        connection.close();

}
}
