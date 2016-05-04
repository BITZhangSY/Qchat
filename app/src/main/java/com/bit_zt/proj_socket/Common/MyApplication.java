package com.bit_zt.proj_socket.Common;

import android.app.Application;
import android.content.Context;

/**
 * Created by bit_zt on 15/11/3.
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
