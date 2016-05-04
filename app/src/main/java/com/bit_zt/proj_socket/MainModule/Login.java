package com.bit_zt.proj_socket.MainModule;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.bit_zt.proj_socket.Common.ActivityController;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.R;
import com.bit_zt.proj_socket.View.InputBox;

/**
 * Created by bit_zt on 15/12/9.
 */
public class Login extends Activity {

    private InputBox inputBox_account;
    private InputBox inputBox_password;

    private Button login;
    private Button register;

    private SharedPreferences preferences;

    private String str_account;
    private String str_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        ActivityController.addActivity(this);

        init();
    }

    private void init() {

        inputBox_account = (InputBox) findViewById(R.id.inputBox_account);
        inputBox_password = (InputBox) findViewById(R.id.inputBox_password);

        inputBox_account.img.setImageResource(R.drawable.mm_title_btn_contact_normal);
        inputBox_password.img.setImageResource(R.drawable.mm_title_btn_newmail_normal);

        inputBox_password.editText.setHint("Password");

        inputBox_password.editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputBox_password.editText.setTypeface(Typeface.DEFAULT);
        inputBox_password.editText.setTransformationMethod(new PasswordTransformationMethod());

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        preferences = Utils.getPreferences();

        initEvents();

    }

    private void initEvents() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null == str_account || null == str_password){
                    Toast.makeText(Login.this,"user information can not be empty!",Toast.LENGTH_LONG).show();
                }else if(false == preferences.getBoolean("isLogin",false)){  //账户信息存在且输入正确则登录成功
                    if(str_account == preferences.getString("account",null) &&
                            str_password == preferences.getString("password",null)){

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("isLogin", true);
                        editor.commit();

                        jumpToMain();
                    }
                }else {
                    Toast.makeText(Login.this,"user information does not exist!",Toast.LENGTH_LONG).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null == str_account || null == str_password){
                    Toast.makeText(Login.this,"user information can not be empty!",Toast.LENGTH_LONG).show();
                }else{
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("account",str_account);
                    editor.putString("password",str_password);
                    editor.putBoolean("isLogin", true);
                    editor.commit();

                    jumpToMain();
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
                str_account = s.toString();
            }
        });

        inputBox_password.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                str_password = s.toString();
            }
        });
    }

    private void jumpToMain() {

        Intent intent = new Intent(Login.this,MainActivity.class);
        startActivity(intent);

    }
}
