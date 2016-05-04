package com.bit_zt.proj_socket.View;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bit_zt.proj_socket.R;

/**
 * Created by bit_zt on 15/12/10.
 */
public class TitleBarLayout extends LinearLayout{

    public LinearLayout layout_back;
    public TextView tv_module;
    public Button save;

    public TitleBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.titlebar_back, this);

        layout_back = (LinearLayout) findViewById(R.id.layout_back);
        tv_module = (TextView) findViewById(R.id.tv_Module);
        save = (Button) findViewById(R.id.titlebar_save).findViewById(R.id.save);

        layout_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });

        save.setEnabled(false);

    }
}
