package com.bit_zt.proj_socket.SubModuleActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bit_zt.proj_socket.Common.ActivityController;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.R;
import com.bit_zt.proj_socket.View.TitleBarLayout;

/**
 * Created by bit_zt on 15/12/9.
 */
public class ChangeMyInfo extends Activity {

    private TitleBarLayout titleBarLayout;

    private EditText editText_change;

    private int change_type;
    private String key;
    private String defaultValue;

    private String changeValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_myinfo);

        ActivityController.addActivity(this);

        init();
    }

    private void init() {

        Bundle bundle = getIntent().getExtras();

        change_type = bundle.getInt("flag");
        key = bundle.getString("key");
        defaultValue = bundle.getString("deValue", "");

        titleBarLayout = (TitleBarLayout) findViewById(R.id.titleBarLayout_change);
        titleBarLayout.tv_module.setText(bundle.getString("title"));
        titleBarLayout.save.setVisibility(View.VISIBLE);
        titleBarLayout.save.setBackgroundResource(R.drawable.background_bt_send_pressed);

        editText_change = (EditText) findViewById(R.id.edittext_change);
        editText_change.setText(defaultValue);

        initEvents();
    }

    private void initEvents() {

        titleBarLayout.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getPreferences().edit().putString(key,changeValue).commit();
                Toast.makeText(ChangeMyInfo.this,"修改成功",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        editText_change.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //如果与默认值相同,则保存键不可用
                if(defaultValue.equals(s.toString())){
                    titleBarLayout.save.setEnabled(false);
                    titleBarLayout.save.setBackgroundResource(R.drawable.background_bt_send_pressed);
                }else{
                    titleBarLayout.save.setEnabled(true);
                    titleBarLayout.save.setBackgroundResource(R.drawable.selector_bt_send);
                }

                changeValue = s.toString();
            }
        });
    }
}
