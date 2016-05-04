package com.bit_zt.proj_socket.MainModule;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import com.bit_zt.proj_socket.Common.ActivityController;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.R;

/**
 * Created by bit_zt on 15/12/9.
 */
public class Welcome extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);

        ActivityController.addActivity(this);

        delay();
    }

    private void delay(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //若仍是登录状态则直接进入
                SharedPreferences sharedPreferences = Utils.getPreferences();
                boolean is_Login = sharedPreferences.getBoolean("isLogin", false);

                Intent intent = new Intent(Welcome.this, Login.class);
                if(is_Login){
                    intent.setClass(Welcome.this, MainActivity.class);
                }
                startActivity(intent);

            }
        }).start();
    }

}
