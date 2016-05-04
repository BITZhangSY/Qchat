package com.bit_zt.proj_socket.ThreadClass;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bit_zt.proj_socket.Common.BitmapUtils;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.DataSet.Terminal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by bit_zt on 15/11/3.
 *
 * search other device and try to make connection
 */
public class SearchThread extends Thread {

    private Handler handler;

    public boolean handle_loop = true;

    public SearchThread(Handler handler) {
        this.handler = handler;
    }


    @Override
    public void run() {

        String LanTypeCutIp = Utils.cutStringIntoLanType(Utils.ipAddress);

        String conn_req = "IP: " + Utils.ipAddress + "  " + Utils.deviceName + "want to connect to you!";
        Terminal myDevice = new Terminal(Utils.getDeviceName(), Utils.ipAddress, conn_req, false);
        myDevice.setUserAccount(Utils.getPreferences().getString("account",""));
        myDevice.setUserNickname(Utils.getPreferences().getString("nickname", ""));
        myDevice.setHeadshowStream(BitmapUtils.DrawableToString(BitmapUtils.getDrawble(BitmapUtils.headShowName)));

        while(handle_loop){

            for(int i=0; i<256; i++){
                String possibleTargetIp = LanTypeCutIp + i;
                Log.e("possibleAdd",possibleTargetIp);
                //如果是自己，则跳过
                if(possibleTargetIp.equals(Utils.ipAddress)){
                    continue;
                }
                try {

                    if(possibleTargetIp.equals("192.168.1.106")){
                        Log.e("possible","aa");
                    }
//                    String test = "192.168.1.102";
                    InetSocketAddress inetSocketAddress = new InetSocketAddress(possibleTargetIp, Utils.PORT);
                    if(possibleTargetIp.equals(Utils.ipAddress)){
                        continue;
                    }
                    Socket socket = new Socket();
                    socket.connect(inetSocketAddress, 1000);

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                            new BufferedOutputStream(socket.getOutputStream()));
                    objectOutputStream.writeObject(myDevice);
                    objectOutputStream.flush();

                    ObjectInputStream objectInputStream = new ObjectInputStream(
                                                    new BufferedInputStream(socket.getInputStream()));


                    Object object = objectInputStream.readObject();

                    // 得到回应
                    if(null != object){
                        Terminal server = (Terminal) object;

                        Utils.connect_info.put(server.getDeviceName(), server);

                        Drawable drawable = BitmapUtils.StringToDrawable(server.getHeadshowStream());
                        Utils.user_headshow.put(server.getDeviceName(),drawable);

                        //发送消息到主线程，有新用户接入更新listview
                        Message message = new Message();
                        message.what = Utils.msgWhat_NEW_TERMINAL_FOUND;

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("newTerminal",server);

                        message.setData(bundle);
                        handler.sendMessage(message);
                    }

                    objectOutputStream.close();
                    objectInputStream.close();
                    socket.close();

                } catch (IOException e) {
                    Log.e("Exception", e.toString());
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    Log.e("Exception", e.toString());
                    e.printStackTrace();
                }

            }
        }
    }
}
