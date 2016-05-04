package com.bit_zt.proj_socket.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import com.bit_zt.proj_socket.DataSet.ChatMsgEntity;
import com.bit_zt.proj_socket.DataSet.ContactsEntity;
import com.bit_zt.proj_socket.DataSet.Terminal;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by bit_zt on 15/11/3.
 */
public class Utils {

    public static final int PORT = 11140;
    public static final int PROT_B = 9999;
    public static final int msgWhat_NEW_TERMINAL_FOUND = 1114;

    public static final String BROADCASTRECEIVER_ACTION = "com.bit_zt.proj_ListenMsg";
    public static final String BROADCASTRECEIVER_ADDFRIEND = "com.bit_zt.proj_AddFriend";
    public static final String TERMINAL_BUNDLE_KEY = "ChatMsg";

    public static String deviceName = Utils.getDeviceName();
    public static String ipAddress = Utils.getIP();

    private static SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("user_info",Context.MODE_PRIVATE);

    //当前所有互联设备信息
    public static List<Terminal> user_infoList;
    //当前所有消息
    public static List<ChatMsgEntity> msg_list = new ArrayList<>();
    //互联设备头像
    public static HashMap<String,Drawable> user_headshow = new HashMap<>();

    /*
    *   @param  String: User Name
    *           User_Info:  All info about a user(Terminal, socket)
    * */
    public static HashMap<String,Terminal> connect_info = new HashMap<String, Terminal>();

    public static String getDeviceName(){
        return Build.MODEL;
    }

    public static String getIP(){

        WifiManager wifiManager = (WifiManager)MyApplication.getContext().getSystemService(Context.WIFI_SERVICE);

        if(!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        int ip = wifiInfo.getIpAddress();
        String ipAddress = intToIp(ip);
        Log.e("localIp", ipAddress);

        return ipAddress;
    }

    public static String intToIp(int ip){
        return  (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                ((ip >> 24) & 0xFF);

    }

    public static String cutStringIntoLanType(String str){

        int dot;

        dot = str.indexOf(".",0);
        dot = str.indexOf(".",dot + 1);
        dot = str.indexOf(".",dot + 1);

        return str.substring(0,dot + 1);
    }

    public static <T> List<T> Traverse_HashMap(HashMap<String, T> hashMap){

        List<T> list = new ArrayList<>();

        Iterator iterator = hashMap.entrySet().iterator();

        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();

            T t = (T) entry.getValue();
            list.add(t);
        }

        return list;
    }

    public static List<Terminal> refresh_UserInfo(){
        user_infoList = Utils.Traverse_HashMap(Utils.connect_info);
        return user_infoList;
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static String getTimeNow(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static ContactsEntity getContactsEntity(Terminal terminal) {

        ContactsEntity contactsEntity = new ContactsEntity(terminal.getDeviceName(),
                terminal.getUserNickname(), terminal.getUserAccount());

        CharacterParser characterParser = CharacterParser.getInstance();

        //将昵称首字转换为拼音
        String pinyin = characterParser.getSelling(terminal.getUserAccount().substring(0, 1));
        String sortLetter = pinyin.substring(0, 1).toUpperCase();
        String sortPinyin = pinyin.toUpperCase();

        //正则表达式,判断首字母是否是英文字母
        if (sortLetter.matches("[A-Z]")) {
            contactsEntity.setSortLetter(sortLetter);
            contactsEntity.setSortPinyin(sortPinyin);
        } else {
            contactsEntity.setSortLetter("#");
            contactsEntity.setSortPinyin("#");
        }

        return contactsEntity;
    }
}
