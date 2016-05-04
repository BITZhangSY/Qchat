package com.bit_zt.proj_socket.SubModuleActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bit_zt.proj_socket.Common.ActivityController;
import com.bit_zt.proj_socket.Common.CharacterParser;
import com.bit_zt.proj_socket.Common.MyDatabaseHelper;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.DataSet.ContactsEntity;
import com.bit_zt.proj_socket.DataSet.Terminal;
import com.bit_zt.proj_socket.R;
import com.bit_zt.proj_socket.View.InputBox;
import com.bit_zt.proj_socket.View.TitleBarLayout;

/**
 * Created by bit_zt on 15/12/10.
 */
public class AddFriends extends Activity {

    private TitleBarLayout titleBarLayout;

    private InputBox inputBox_account;
    private InputBox inputBox_nickname;

    private boolean judge_if_null;

    private MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addfriends);

        ActivityController.addActivity(this);

        init();
    }

    private void init() {

        titleBarLayout = (TitleBarLayout) findViewById(R.id.titleBarLayout_addFriends);

        titleBarLayout.tv_module.setText("Friendsss");
        titleBarLayout.save.setEnabled(false);
        titleBarLayout.save.setVisibility(View.VISIBLE);
        titleBarLayout.save.setBackgroundResource(R.drawable.background_bt_send_pressed);

        inputBox_account = (InputBox) findViewById(R.id.inputBox_addAccount);
        inputBox_nickname = (InputBox) findViewById(R.id.inputBox_addNickname);

        inputBox_account.editText.setTextColor(getResources().getColor(R.color.black));
        inputBox_nickname.editText.setTextColor(getResources().getColor(R.color.black));
        inputBox_nickname.editText.setHint("Nickname");
        inputBox_nickname.img.setImageResource(R.drawable.mm_title_btn_album_nor);

        databaseHelper = new MyDatabaseHelper(AddFriends.this,"Qchat.db",null,1);

        initEvents();
    }

    private void initEvents() {
        titleBarLayout.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(judge_if_null){  //如果两输入框都非空
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();

                    Terminal terminal = new Terminal("",inputBox_account.editText.getText().toString(),
                                                        inputBox_nickname.editText.getText().toString());
                    ContactsEntity contactsEntity = Utils.getContactsEntity(terminal);
                    ContentValues values = new ContentValues();
                    values.put("account",contactsEntity.getuserAccount());
                    values.put("deviceName",contactsEntity.getDeviceName());
                    values.put("nickname",contactsEntity.getuserNickname());
                    values.put("sortLetter", contactsEntity.getSortLetter());
                    values.put("sortPinyin", contactsEntity.getSortPinyin());

                    db.insert("contact", null, values);

                    finish();
                }
            }
        });

        inputBox_account.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    judge_if_null = false;
                } else {
                    judge_if_null = true;
                }

                if (judge_if_null) {
                    titleBarLayout.save.setEnabled(true);
                    titleBarLayout.save.setBackgroundResource(R.drawable.selector_bt_send);
                }
            }
        });
        inputBox_nickname.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    judge_if_null = false;
                } else {
                    judge_if_null = true;
                }

                if(judge_if_null){
                    titleBarLayout.save.setEnabled(true);
                    titleBarLayout.save.setBackgroundResource(R.drawable.selector_bt_send);
                }
            }
        });
    }
}
