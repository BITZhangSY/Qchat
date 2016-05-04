package com.bit_zt.proj_socket.DataSet;

import java.io.Serializable;

/**
 * Created by bit_zt on 15/11/7.
 */
public class ChatMsgEntity implements Serializable{

    private static final long serialVersionUID = -8435475341279516275L;
    private String name;
    private String text;
    private String date;

    private boolean isMyMsg = false;


    public ChatMsgEntity() {
    }

    public ChatMsgEntity(String name, String text, String date, boolean isMyMsg) {
        this.name = name;
        this.text = text;
        this.date = date;
        this.isMyMsg = isMyMsg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getMsgType() {
        return isMyMsg;
    }

    public void setMsgType(boolean isMyMsg) {
        this.isMyMsg = isMyMsg;
    }
}
