package com.bit_zt.proj_socket.TabFragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bit_zt.proj_socket.Common.MyDatabaseHelper;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.View.Adapter_Contacts;
import com.bit_zt.proj_socket.Common.MyApplication;
import com.bit_zt.proj_socket.Common.PinyinComparator;
import com.bit_zt.proj_socket.View.SideBar;
import com.bit_zt.proj_socket.DataSet.ContactsEntity;
import com.bit_zt.proj_socket.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bit_zt on 15/11/8.
 */
public class FragmentNone extends Fragment {

    private View rootview;

    private SideBar sideBar;
    private TextView LetterClicked;
    private Adapter_Contacts adapter_contacts;

    private ListView lv_chatContacts;

    private List<ContactsEntity> datalist;

    private MyDatabaseHelper databaseHelper;

    private PinyinComparator comparator = new PinyinComparator();

    private AddFriendReceiver friendReceiver;

    private class AddFriendReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Utils.BROADCASTRECEIVER_ADDFRIEND)){
                refreshListView();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.tab_fragment_none, container, false);
        return rootview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        friendReceiver = new AddFriendReceiver();
        IntentFilter filter = new IntentFilter(Utils.BROADCASTRECEIVER_ADDFRIEND);
        getContext().registerReceiver(friendReceiver,filter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

    }

    private void init() {

        databaseHelper = new MyDatabaseHelper(getContext(),"Qchat.db",null,1);

        sideBar = (SideBar) rootview.findViewById(R.id.sidebar);
        LetterClicked = (TextView) rootview.findViewById(R.id.tv_letter);

        lv_chatContacts = (ListView) rootview.findViewById(R.id.lv_chatContacts);
        datalist = getContactsFromDB();

        setListener();

        adapter_contacts = new Adapter_Contacts(MyApplication.getContext(),datalist);
        lv_chatContacts.setAdapter(adapter_contacts);
    }

    private List<ContactsEntity> getContactsFromDB() {
        List<ContactsEntity> list = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query("contact",null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                ContactsEntity entity = new ContactsEntity();
                entity.setuserAccount(cursor.getString(cursor.getColumnIndex("account")));
                entity.setuserNickname(cursor.getString(cursor.getColumnIndex("nickname")));
                entity.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                entity.setSortLetter(cursor.getString(cursor.getColumnIndex("sortLetter")));
                entity.setSortPinyin(cursor.getString(cursor.getColumnIndex("sortPinyin")));
                list.add(entity);
            }while(cursor.moveToNext());
        }
        return list;
    }

    private void setListener() {
        sideBar.setTextView(LetterClicked);

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String letter) {

                int position = adapter_contacts.getPositionForSection(letter.charAt(0));

                if (position != -1) {
                    lv_chatContacts.setSelection(position);
                }
            }
        });

        //通讯录item长按,弹框,删除
        lv_chatContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(getContext())
                        .setItems(new String[]{"删除"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                                db.delete("contact","account = ?",
                                            new String[]{datalist.get(position).getuserAccount()});

                                refreshListView();
                            }
                        })
                        .show();

                return true;
            }
        });
    }

    private void refreshListView(){
        datalist = getContactsFromDB();
        adapter_contacts.refresh(datalist);

        Collections.sort(datalist,comparator);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshListView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(friendReceiver);
    }
}
