package com.mc.chat_2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mc.Client;
import com.mc.adapter.MessageAdapter;
import com.mc.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private List<com.mc.entity.Message> msgList = new ArrayList<>();
    private EditText ed;
    private Button bt;
    private ImageButton ib,ib2;
    private RecyclerView recyclerView;
    private MessageAdapter mAdapter;
    private Client client;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    String message = (String) msg.obj;

                    com.mc.entity.Message message1 = new com.mc.entity.Message(message, com.mc.entity.Message.TYPE_RECEIVED);
                    msgList.add(message1);
                    mAdapter.notifyItemInserted(msgList.size()-1);
                    recyclerView.scrollToPosition(msgList.size() - 1);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        initMsg();

        ed = findViewById(R.id.main_et);
        bt = findViewById(R.id.main_bt);
        ib = findViewById(R.id.main_img_bt);
        ib2 =findViewById(R.id.main_img_bt2);

        recyclerView = findViewById(R.id.main_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new MessageAdapter(msgList);
        recyclerView.setAdapter(mAdapter);

        client = new Client(handler);
        client.start();

        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.showShortMsg(getApplicationContext(),"完善中😁🎉♻...");
            }
        });

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.showShortMsg(getApplicationContext(),"完善中😁🎉♻...");
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = ed.getText().toString().trim();

                if(!TextUtils.isEmpty(msg)){
                    com.mc.entity.Message message = new com.mc.entity.Message(msg, com.mc.entity.Message.TYPE_SENT);
                    msgList.add(message);
                    mAdapter.notifyItemInserted(msgList.size() - 1);
                    recyclerView.scrollToPosition(msgList.size() - 1);
                    ed.setText("");
                    client.sendMessage(msg);
                }

                CommonUtils.showShortMsg(getApplicationContext(),"消息不能为空");
            }
        });
    }

//    private void initMsg(){
//        com.mc.entity.Message msg1=new com.mc.entity.Message("Hello guy.", com.mc.entity.Message.TYPE_RECEIVED);
//        msgList.add(msg1);
//        com.mc.entity.Message msg2=new com.mc.entity.Message("Hello.Who is that?", com.mc.entity.Message.TYPE_SENT);
//        msgList.add(msg2);
//        com.mc.entity.Message msg3=new com.mc.entity.Message("This is Tom!", com.mc.entity.Message.TYPE_RECEIVED);
//        msgList.add(msg3);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.close();
    }
}
