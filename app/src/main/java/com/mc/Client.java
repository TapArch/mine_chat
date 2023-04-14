package com.mc;

import android.os.Message;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread{
    private final String HOST = "47.113.206.22";
    private final int PORT = 19524;
    private Socket socket = null;
    private PrintWriter pw;
    private BufferedReader br;
    private Handler handler;

    public Client(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(HOST, PORT);
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = null;
            while ((message = br.readLine()) != null){
                Message msg = new Message();
                msg.what = 1;
                msg.obj = message;
                handler.sendMessage(msg);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(final String msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                pw.println(msg);
            }
        }).start();
    }

    public void close(){
        try {
            if (socket != null) {
                socket.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
