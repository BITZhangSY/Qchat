package com.bit_zt.proj_socket.ThreadClass;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bit_zt.proj_socket.Common.BitmapUtils;
import com.bit_zt.proj_socket.Common.MyApplication;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.DataSet.Terminal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by bit_zt on 15/11/3.
 *
 * listen from other device as a server, include Connect request and Message
 */
public class ListenThread extends Thread {


    private ServerSocket serverSocket;

    private Handler handler;

    public boolean handle_loop = true;

    private Context context;

    public ListenThread(Handler handler) {
        this.handler = handler;
    }

    public ListenThread(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void run() {

        String sayHi = "IP: " + Utils.ipAddress + "  " + Utils.deviceName + "allow the request!";

        Terminal myDevice = new Terminal(Utils.getDeviceName(), Utils.ipAddress,sayHi,false);
        myDevice.setUserAccount(Utils.getPreferences().getString("account",""));
        myDevice.setUserNickname(Utils.getPreferences().getString("nickname",""));

        while(handle_loop){
            try {
                serverSocket = new ServerSocket(Utils.PORT);
                Socket socket = serverSocket.accept();


                ObjectInputStream objectInputStream = new ObjectInputStream(
                        new BufferedInputStream(socket.getInputStream()));

                Object object = objectInputStream.readObject();

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                        new BufferedOutputStream(socket.getOutputStream()));

                if(null != object){
                    Terminal client = (Terminal) object;

                    boolean flag = client.getIsChatMsg();

                    //若是消息
                    if(flag){
                        client.getMsgEntity().setMsgType(false);
                        Utils.msg_list.add(client.getMsgEntity());

                        Intent intent = new Intent(Utils.BROADCASTRECEIVER_ACTION);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Utils.TERMINAL_BUNDLE_KEY, client);
                        intent.putExtras(bundle);

                        MyApplication.getContext().sendBroadcast(intent);
                    }
                    //请求信息 若有存储该对象信息 则不添加联系人表中 且发送null给申请方
                    else{
                        if(null == Utils.connect_info.get(client.getDeviceName())){
                            Utils.connect_info.put(client.getDeviceName(), client);

                            Drawable drawable = BitmapUtils.StringToDrawable(client.getHeadshowStream());
                            Utils.user_headshow.put(client.getDeviceName(),drawable);

                            //发送消息到主线程，有新用户接入更新listview
                            Message message = new Message();
                            message.what = Utils.msgWhat_NEW_TERMINAL_FOUND;

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("newTerminal",client);

                            message.setData(bundle);
                            handler.sendMessage(message);
                        }else{
                            myDevice = null;
                        }

                        objectOutputStream.writeObject(myDevice);
                        objectOutputStream.flush();
                    }


                }
                objectOutputStream.close();
                objectInputStream.close();

                socket.close();
                serverSocket.close();

            } catch (IOException e) {
                Log.e("Exception","serverSocket");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                Log.e("Exception", "classNotFound");
                e.printStackTrace();
            }
        }
    }
}
