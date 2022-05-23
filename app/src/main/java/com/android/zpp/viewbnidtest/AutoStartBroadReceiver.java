package com.android.zpp.viewbnidtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * @ProjectName: ViewBnidTest
 * @Package: com.android.zpp.viewbnidtest
 * @ClassName: AutoStartBroadReceiver
 * @Description:
 * @Author: zpp
 * @CreateDate: 2022/5/13 13:47
 * @UpdateUser:
 * @UpdateDate: 2022/5/13 13:47
 * @UpdateRemark:
 */
public class AutoStartBroadReceiver extends BroadcastReceiver {
    private static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("接收广播", "onReceive: ");
        Log.e("接收广播", "onReceive: " + intent.getAction());
        //开机启动
        if (ACTION.equals(intent.getAction())) {
            Log.e("接收广播", "onReceive: 启动了。。。");

//第一种方式：根据包名
//            PackageManager packageManager = context.getPackageManager();
//            Intent mainIntent = packageManager.getLaunchIntentForPackage("com.harry.martin");
//            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mainIntent);
//            context.startService(mainIntent);


//第二种方式：指定class类，跳转到相应的Acitivity
//            Intent mainIntent = new Intent(context, MainActivity.class);
//            /**
//             * Intent.FLAG_ACTIVITY_NEW_TASK
//             * Intent.FLAG_ACTIVITY_CLEAR_TOP
//             */
//            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mainIntent);
            Intent mainIntent = new Intent(context, MyServer.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(mainIntent);
            }else {
                context.startService(mainIntent);
            }
        }
    }
}
