package com.bit_zt.proj_socket.MainModule;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.ThreadClass.ListenThread;
import com.bit_zt.proj_socket.ThreadClass.SearchThread;

/**
 * Created by bit_zt on 15/11/2.
 *
 * SocketService is some kind of Service which manage all socket
 * background job. Expose a SocketBinder to provide interaction with other device.
 *
 */
public class SocketService extends Service {

    private SocketBinder socketBinder = new SocketBinder(handler);

    public static Handler handler;
    /*
        *   build as a binder for method onBind() reture use.
        *   implements the most main function about socket communication.
        *
        */
    public class SocketBinder extends Binder{

        private Handler handler;

        private ListenThread listenThread;
        private SearchThread searchThread;

        public SocketBinder(Handler handler) {
            this.handler = handler;
            this.listenThread = new ListenThread(handler);
            this.searchThread = new SearchThread(handler);
        }

        public void StartListen(){
            listenThread.start();
        }

        public void StartSearch(){
            searchThread.start();
        }

        public void shutListenThread(){
            listenThread.handle_loop = false;
        }

        public void shutSearchThread(){
            searchThread.handle_loop = false;
        }
    }

    @Override
    /*
    * @param callback when method bindService(Intent MyIntent,**,**) is called,
    *        where MyIntent is intent as used here.
    *        it can be used as bundle trans delivery.
    * */
    public IBinder onBind(Intent intent) {
        return socketBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
