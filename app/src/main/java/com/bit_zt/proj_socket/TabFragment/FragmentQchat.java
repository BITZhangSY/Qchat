package com.bit_zt.proj_socket.TabFragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.bit_zt.proj_socket.Common.MyDatabaseHelper;
import com.bit_zt.proj_socket.DataSet.ContactsEntity;
import com.bit_zt.proj_socket.View.Adapter_chatList;
import com.bit_zt.proj_socket.Common.MyApplication;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.DataSet.Terminal;
import com.bit_zt.proj_socket.MainModule.MainActivity;
import com.bit_zt.proj_socket.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bit_zt on 15/11/8.
 */
public class FragmentQchat extends Fragment{

    private MainActivity MyActivity;

    private Context context;

    View rootview;

    private MyDatabaseHelper databaseHelper;

    //chat list
    private List<Terminal> datalist = new ArrayList<>();
    private ListView ChatList_lv;
    private Adapter_chatList adapter_chatList;

    private Handler handler_forFragment1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {

                Bundle bundle = msg.getData();
                Terminal terminal = (Terminal) bundle.get("newTerminal");

                datalist.clear();
                datalist = Utils.refresh_UserInfo();

                adapter_chatList.refresh(datalist);

        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyActivity = (MainActivity) getActivity();
        MyActivity.setHandler_forTab1(handler_forFragment1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.tab_fragment_qchat,container,false);

        initView();

        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvents();
    }

    private void initView() {

        context = MyApplication.getContext();

        databaseHelper = new MyDatabaseHelper(getContext(),"Qchat.db",null,1);

        //findViewById
        ChatList_lv = (ListView) rootview.findViewById(R.id.ChatList_lv);

        //to traverse the HashMap
        datalist = Utils.refresh_UserInfo();
        adapter_chatList = new Adapter_chatList(context, datalist);
        ChatList_lv.setAdapter(adapter_chatList);
    }

    private void initEvents() {

        ChatList_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //弹出提示框-加为联系人
                new AlertDialog.Builder(getContext())
                        .setItems(new String[]{"添加"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                                String account = datalist.get(position).getUserAccount();
                                String sql = "select * from contact where account=?";
                                Cursor cursor = db.rawQuery(sql,new String[]{account});
                                if(cursor.moveToFirst()){
                                    Toast.makeText(context, "this information has already been stored"
                                                    ,Toast.LENGTH_LONG).show();
                                }else{
                                    ContactsEntity contactsEntity = Utils.getContactsEntity(datalist.get(position));
                                    ContentValues values = new ContentValues();
                                    values.put("account",contactsEntity.getuserAccount());
                                    values.put("deviceName",contactsEntity.getDeviceName());
                                    values.put("nickname",contactsEntity.getuserNickname());
                                    values.put("sortLetter", contactsEntity.getSortLetter());
                                    values.put("sortPinyin", contactsEntity.getSortPinyin());
                                    db.insert("contact", null, values);

                                    Toast.makeText(context, "contact information is stored successfully"
                                                    ,Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(Utils.BROADCASTRECEIVER_ADDFRIEND);
                                    context.sendBroadcast(intent);
                                }
                            }
                        })
                        .show();

                return true;
            }
        });


    }
}
