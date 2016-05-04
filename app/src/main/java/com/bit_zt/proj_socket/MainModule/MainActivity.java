package com.bit_zt.proj_socket.MainModule;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bit_zt.proj_socket.Common.ActivityController;
import com.bit_zt.proj_socket.R;
import com.bit_zt.proj_socket.View.Adapter_mainViewPager;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.DataSet.Terminal;
import com.bit_zt.proj_socket.TabFragment.FragmentMe;
import com.bit_zt.proj_socket.TabFragment.FragmentNone;
import com.bit_zt.proj_socket.TabFragment.FragmentQchat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private Context context;

    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();

    private LinearLayout layout_tab1;
    private LinearLayout layout_tab2;
    private LinearLayout layout_tab3;

    //socket
    private SocketService.SocketBinder MyBinder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBinder = (SocketService.SocketBinder) service;

            MyBinder.StartListen();
            MyBinder.StartSearch();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == Utils.msgWhat_NEW_TERMINAL_FOUND){
                Log.e("haha", "handle");

                Bundle bundle = msg.getData();
                Terminal terminal = (Terminal) bundle.get("newTerminal");

                Toast.makeText(context, terminal.getMsg(), Toast.LENGTH_LONG).show();

                Message message = new Message();
                message.setData(bundle);

                handler_forTab1.sendMessage(message);
            }
        }
    };

    private Handler handler_forTab1;

    public void setHandler_forTab1(Handler handler_forTab1){
        this.handler_forTab1 = handler_forTab1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityController.addActivity(this);

        //initial settings and actions
        init();
    }

    /*、
    *   execute initial settings and actions
    *
    * */
    private void init() {

        context = this;

        //set ActionBar things
        setOverflowButtonAlways();
        getActionBar().setDisplayShowHomeEnabled(false);

        //initalize the parameters
        SocketService.handler = this.handler;

        //viewPager & fragment
        viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        Fragment fragment_Qchat = new FragmentQchat();
        Fragment fragment_None = new FragmentNone();
        Fragment fragment_Me = new FragmentMe();

        layout_tab1 = (LinearLayout) findViewById(R.id.bottombar).findViewById(R.id.layout_bottom_tab1);
        layout_tab2 = (LinearLayout) findViewById(R.id.bottombar).findViewById(R.id.layout_bottom_tab2);
        layout_tab3 = (LinearLayout) findViewById(R.id.bottombar).findViewById(R.id.layout_bottom_tab3);

        fragmentList.add(fragment_Qchat);
        fragmentList.add(fragment_None);
        fragmentList.add(fragment_Me);

        viewPager.setAdapter(new Adapter_mainViewPager(getSupportFragmentManager(),fragmentList));
        viewPager.setCurrentItem(0);

        //启动服务
        Intent intent = new Intent("com.bit_zt.proj.SocketService");
        bindService(intent, conn, BIND_AUTO_CREATE);
        startService(intent);

        //各类点击事件、监听事件
        initAllKindsEvens();

    }

    private void initAllKindsEvens() {

        layout_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        layout_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        layout_tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
    *   利用反射强制限制actionbar中的overflow
    * */
    private void setOverflowButtonAlways()
    {
        try {
            ViewConfiguration config = ViewConfiguration.get(context);
            Field menuKey = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(config, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    /*
    *   shut down the background Service
    * */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        MyBinder.shutListenThread();
        MyBinder.shutSearchThread();

        Intent intent = new Intent("com.bit_zt.proj.SocketService");
        unbindService(conn);
        stopService(intent);
    }


}
