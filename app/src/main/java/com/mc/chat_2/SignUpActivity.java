package com.mc.chat_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mc.user.UserDao;
import com.mc.user.UserInfo;
import com.mc.utils.CommonUtils;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt;
    private EditText et,et2;

    private Handler main_3_handler;
    private UserDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView(); //初始化方法
    }

    private void initView() {
        //对控件进行初始化
        bt = findViewById(R.id.main3_bt);
        et = findViewById(R.id.main3_et);
        et2 = findViewById(R.id.main3_et2);

        dao = new UserDao();
        main_3_handler = new Handler(getMainLooper());

        //创建点击事件
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main3_bt:
                doSignUp();
        }
    }

    private void doSignUp(){
        //获取注册页面的EditText的值
        String account = et.getText().toString().trim();
        String password = et2.getText().toString().trim();

        //判断是否为Null、空       return str == null || str.length() == 0;
        if(TextUtils.isEmpty(account)){
            CommonUtils.showShortMsg(this,"用户名不能为空");
        }else if(TextUtils.isEmpty(password)){
            CommonUtils.showShortMsg(this,"密码不能为空");
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserInfo user = new UserInfo(account,password);
                    Boolean flag = dao.addUser(user);

                    main_3_handler.post(new Runnable() {
                        @Override
                        public void run() {
                             if(flag){
                                 CommonUtils.showLongMsg(SignUpActivity.this,"注册......OK");
                                 Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                 startActivity(intent);
                                 finish();
                             }else {
                                 CommonUtils.showDlgMsg(SignUpActivity.this,"注册......No\n" + "存在相同用户名，换个更帅气的名字吧！");
                                 et.requestFocus();
                             }
                        }
                    });
                }
            }).start();
        }
    }
}