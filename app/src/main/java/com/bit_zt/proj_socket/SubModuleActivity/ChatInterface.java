package com.bit_zt.proj_socket.SubModuleActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.bit_zt.proj_socket.Common.ActivityController;
import com.bit_zt.proj_socket.R;
import com.bit_zt.proj_socket.View.Adapter_chatInterface;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.DataSet.ChatMsgEntity;
import com.bit_zt.proj_socket.DataSet.Terminal;
import com.bit_zt.proj_socket.View.TitleBarLayout;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bit_zt on 15/11/6.
 */
public class ChatInterface extends Activity {

    private Context context;

    private TitleBarLayout titleBarLayout;

    private ImageButton ibt_deleteText;
    private Button bt_sendMsg;
    private EditText et_contentMsg;

    private List<ChatMsgEntity> datalist = new ArrayList<>();
    private ListView ChatRecord_lv;
    private Adapter_chatInterface adapter_chatInterface;

    private ChatMsgReceiver msgReceiver;

    private ObjectOutputStream outputStream;

    private Terminal mydevice;

    private String myName;

    private class ChatMsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(Utils.BROADCASTRECEIVER_ACTION)){

                Message message = new Message();
                message.setData(intent.getExtras());

                handler.sendMessage(message);
            }
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            Terminal terminal = (Terminal) bundle.getSerializable(Utils.TERMINAL_BUNDLE_KEY);

            //datalist 已经引用了Utils.msg_list,不需要再添加一次
            adapter_chatInterface.refresh(datalist);
            ChatRecord_lv.setSelection(ChatRecord_lv.getCount() - 1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.chat_interface);

        ActivityController.addActivity(this);

        init();
    }

    private void init() {

        context = this;
        titleBarLayout = (TitleBarLayout) findViewById(R.id.titleBarLayout_chat);
        titleBarLayout.tv_module.setText("Chatroom");

        mydevice = new Terminal(Utils.getDeviceName(),Utils.getIP());
        mydevice.setIsChatMsg(true);

        ChatRecord_lv = (ListView) findViewById(R.id.lv_chatContent);
        ibt_deleteText = (ImageButton) findViewById(R.id.bt_deletetext);
        bt_sendMsg = (Button) findViewById(R.id.btn_send);
        et_contentMsg = (EditText) findViewById(R.id.et_contentmsg);

        //init ListView
        datalist = Utils.msg_list;
        adapter_chatInterface = new Adapter_chatInterface(context, datalist);
        ChatRecord_lv.setSelection(ChatRecord_lv.getCount() - 1);
        ChatRecord_lv.setAdapter(adapter_chatInterface);

        //init broadcastReceiver
        msgReceiver = new ChatMsgReceiver();
        IntentFilter filter = new IntentFilter(Utils.BROADCASTRECEIVER_ACTION);
        registerReceiver(msgReceiver, filter);

        myName = mydevice.getDeviceName();

        initAllKindsEvens();
    }

    private void initAllKindsEvens() {

        bt_sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = et_contentMsg.getText().toString();
                if(!msg.equals("")){

                    ChatMsgEntity msgEntity = new ChatMsgEntity(myName,msg,Utils.getTimeNow(),true);
                    datalist.add(msgEntity);
                    adapter_chatInterface.refresh(datalist);
                    ChatRecord_lv.setSelection(ChatRecord_lv.getCount() - 1);

                    mydevice.setMsgEntity(msgEntity);

                    sendMsgWithSocket(mydevice);

                    //发送完消息后把输入框内容清空
                    et_contentMsg.setText("");
                }else {
                    Toast.makeText(context,"发送消息不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });


        ibt_deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_contentMsg.setText("");
            }
        });
    }

    private void sendMsgWithSocket(Terminal terminal) {
        new Send(terminal).start();
    }


    private class Send extends Thread{

        private Terminal terminal;

        public Send(Terminal terminal) {
            this.terminal = terminal;
        }

        @Override
        public void run() {

            for(Terminal target : Utils.user_infoList){
                InetSocketAddress inetSocketAddress = new InetSocketAddress(target.getIpAddress(),Utils.PORT);
                Socket socket = new Socket();
                try {
                    socket.connect(inetSocketAddress,1000);

                    outputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

                    outputStream.writeObject(terminal);
                    outputStream.flush();

                    outputStream.close();
                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(msgReceiver);
    }


}
