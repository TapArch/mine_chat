package com.mc.chat_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mc.user.UserDao;
import com.mc.user.UserInfo;
import com.mc.utils.CommonUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button but,but2;
    private EditText et,et1;

    private Handler mainHandler;
    private UserDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView(){
        but = findViewById(R.id.zc_button);
        but2 = findViewById(R.id.button2);
        et = findViewById(R.id.et);
        et1 = findViewById(R.id.et1);

        dao = new UserDao();
        mainHandler = new Handler(getMainLooper());

        but.setOnClickListener(this);
        but2.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zc_button:
                doSignUp();
                break;
            case R.id.button2:
                doLogin();
                break;
        }
    }

    private void doSignUp(){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void doLogin(){
        String name = et.getText().toString().trim();
        String pwd = et1.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            CommonUtils.showShortMsg(this,"用户名不能为空");
            et.requestFocus();
        }else if(TextUtils.isEmpty(pwd)){
            CommonUtils.showShortMsg(this,"密码不能为空");
            et1.requestFocus();
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserInfo userInfo = dao.getUserByNameAndPass(name,pwd);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(userInfo == null){
                                CommonUtils.showDlgMsg(LoginActivity.this,"用户名或密码错误");
                            }else {
                                //CommonUtils.showDlgMsg(MainActivity.this,"OK");
                                Toast.makeText(LoginActivity.this, name, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }).start();
        }
    }
}