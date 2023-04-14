package com.mc.chat_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mc.Client;
import com.mc.utils.CommonUtils;

import java.io.PrintWriter;
import java.net.Socket;

public class ChatActivity extends AppCompatActivity {
    Button sd_bt;
    TextView sd_tv;
    EditText sd_et;
    private Client client;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    String message = (String) msg.obj;
                    sd_tv.setText(message);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sd_bt = findViewById(R.id.chat_sd_bt);
        sd_tv = findViewById(R.id.chat_se_tv);
        sd_et = findViewById(R.id.chat_sd_et);

        client = new Client(handler);
        client.start();

        sd_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = sd_et.getText().toString().trim();
                client.sendMessage(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.close();
    }
}
