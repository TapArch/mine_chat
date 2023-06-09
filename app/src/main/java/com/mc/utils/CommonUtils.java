package com.mc.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * 自定义通用工具类
 */
public class CommonUtils {

    public static void showShortMsg(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongMsg(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showDlgMsg(Context context, String msg){
        new AlertDialog.Builder(context).setTitle("提示信息")
                .setMessage(msg)
                .setPositiveButton("确定",null)
                .setNegativeButton("取消", null)
                .create().show();
    }

}
