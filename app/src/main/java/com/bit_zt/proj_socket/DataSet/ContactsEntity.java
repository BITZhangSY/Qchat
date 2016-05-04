package com.bit_zt.proj_socket.DataSet;

import android.graphics.drawable.Drawable;

/**
 * Created by bit_zt on 15/11/11.
 */
public class ContactsEntity {

    /*昵称首汉字的拼音首字母,用于显示*/
    private String sortLetter;
    /*昵称首汉字的全拼音,用于排序*/
    private String sortPinyin;

    private String deviceName;
    private String userNickname;
    private String userAccount;

    private Drawable headshow;

    public ContactsEntity() {
    }

    public ContactsEntity(String deviceName, String userNickname, String userAccount) {
        this.deviceName = deviceName;
        this.userNickname = userNickname;
        this.userAccount = userAccount;
    }

    public ContactsEntity(String deviceName, String userNickname, String userAccount, Drawable headshow) {
        this.deviceName = deviceName;
        this.userNickname = userNickname;
        this.userAccount = userAccount;
        this.headshow = headshow;
    }

    public ContactsEntity(String sortLetter, String sortPinyin, String deviceName, String userNickname, String userAccount, Drawable headshow) {
        this.sortLetter = sortLetter;
        this.sortPinyin = sortPinyin;
        this.deviceName = deviceName;
        this.userNickname = userNickname;
        this.userAccount = userAccount;
        this.headshow = headshow;
    }

    public String getSortLetter() {
        return sortLetter;
    }

    public void setSortLetter(String sortLetter) {
        this.sortLetter = sortLetter;
    }

    public String getSortPinyin() {
        return sortPinyin;
    }

    public void setSortPinyin(String sortPinyin) {
        this.sortPinyin = sortPinyin;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getuserNickname() {
        return userNickname;
    }

    public void setuserNickname(String userNickname) {
        userNickname = userNickname;
    }

    public String getuserAccount() {
        return userAccount;
    }

    public void setuserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Drawable getHeadshow() {
        return headshow;
    }

    public void setHeadshow(Drawable headshow) {
        this.headshow = headshow;
    }
}
