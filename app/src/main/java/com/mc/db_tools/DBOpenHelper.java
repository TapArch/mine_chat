package com.mc.db_tools;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOpenHelper {
    private static Connection connection = null;
    private static final String URL = "jdbc:mysql://47.115.210.176:3306/users";
    private static final String USER = "root";
    private static final String PASSWORD = "Root${1110}";

    static int count = 0;

    public static int returnConn(){
        String sql = "select count(user_name) as name from user";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                count = resultSet.getInt("name");
            }

            Log.d("DB",count + "个");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

}
