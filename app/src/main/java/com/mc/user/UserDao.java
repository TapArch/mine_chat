package com.mc.user;

import android.util.Log;

import com.mc.db_tools.MySqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户CURD操作
 * getALlUserList-----------查询所有用户
 * getUserByNameAndPass-----查询用户name&passWord
 * addUser------------------添加用户
 * editUser-----------------更改信息
 * delUser------------------删除用户
 */

public class UserDao extends MySqlHelper {

    private static final String TAG = "TAG";

    private Boolean add_user_flag = true;

    public List<UserInfo> getAllUserList(){
        ArrayList<UserInfo> u_info = new ArrayList<>();
        return u_info;
    }

    public UserInfo getUserByNameAndPass(String name, String password){
        UserInfo userInfo = null;

        try {
            getConnection();
            String sql = "select * from users where user_name=? and user_pwd=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,name);
            preparedStatement.setObject(2,password);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                userInfo = new UserInfo();
                userInfo.setName(name);
                userInfo.setPassword(password);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closedAll();
        }
        return userInfo;
    }

    public Boolean addUser(UserInfo userInfo){
        int row = 0;

        //获取连接
        getConnection();
        //判断是否存在相同用户addCheck();
        if(!(addCheck(userInfo))){
            try {
                String sql = "insert into users value(?,?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setObject(1,userInfo.getName());
                preparedStatement.setObject(2,userInfo.getPassword());
                row = preparedStatement.executeUpdate();

                Log.d(TAG, "Row:" + row);

                if(!(row >= 1)){
                    Log.d(TAG, "Row in:" + row);
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Log.d(TAG,"sql语句存在异常");
            }finally {
                closedAll();
            }
            return add_user_flag;
        }

        Log.d(TAG,"Flag:" + add_user_flag);

        return false;
    }

//    public int edit(UserInfo userInfo){
//        int row = 0;
//        return row;
//    }
//
//    public int delUser(){
//        int row = 0;
//        return row;
//    }


    //存在记录返回T，不存在反之
    public Boolean addCheck(UserInfo userInfo){

        String c_sql = "select count(1) as account from user where user_name = '" + userInfo.getName() + "';";
        int row = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(c_sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            //统计user_name该字段与注册用户相同的数量
            while (resultSet.next()){
                row = resultSet.getInt("account");
                //检查同名该库中已注册用户数量
                if(row >= 1){
                    return true;
                }
                Log.d(TAG, "Flag is t in check");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG,"sql语句存在异常 in check");
        }finally {
            closedAll();
        }
        Log.d(TAG, "Flag is f in check");
        //返回1表示没有存在相同用户，可以添加
        return false;
    }
}
