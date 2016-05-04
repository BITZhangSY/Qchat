package com.bit_zt.proj_socket.DataSet;

import java.io.Serializable;
import java.net.Socket;

/**
 * Created by bit_zt on 15/11/3.
 */
public class Terminal implements Serializable {


    private static final long serialVersionUID = 7730567462605797896L;

    private String deviceName;
    private String userAccount;
    private String userNickname;
    private String ipAddress;
    private String msg;

    private ChatMsgEntity msgEntity;

    private boolean isChatMsg = false;

    private String headshowStream;

    public Terminal(String deviceName, String ipAddress) {
        this.deviceName = deviceName;
        this.ipAddress = ipAddress;
    }

    public Terminal(String deviceName, String ipAddress, String msg, boolean isChatMsg) {
        this.deviceName = deviceName;
        this.ipAddress = ipAddress;
        this.msg = msg;
        this.isChatMsg = isChatMsg;
    }

    public Terminal(String deviceName, String userAccount, String userNickname) {
        this.deviceName = deviceName;
        this.userAccount = userAccount;
        this.userNickname = userNickname;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean getIsChatMsg() {
        return isChatMsg;
    }

    public void setIsChatMsg(boolean isChatMsg) {
        this.isChatMsg = isChatMsg;
    }

    public ChatMsgEntity getMsgEntity() {
        return msgEntity;
    }

    public void setMsgEntity(ChatMsgEntity msgEntity) {
        this.msgEntity = msgEntity;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getHeadshowStream() {
        return headshowStream;
    }

    public void setHeadshowStream(String headshowStream) {
        this.headshowStream = headshowStream;
    }
}
