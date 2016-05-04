package com.bit_zt.proj_socket.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bit_zt.proj_socket.R;

/**
 * Created by bit_zt on 15/12/10.
 */
public class InputBox extends LinearLayout {

    public ImageView img;
    public EditText editText;

    public InputBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.edittext_layout, this);

        img = (ImageView) findViewById(R.id.InputBox_img);
        editText = (EditText) findViewById(R.id.InputBox_edittext);
    }
}
