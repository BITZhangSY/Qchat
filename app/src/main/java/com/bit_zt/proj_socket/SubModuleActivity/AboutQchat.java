package com.bit_zt.proj_socket.SubModuleActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bit_zt.proj_socket.Common.ActivityController;
import com.bit_zt.proj_socket.R;
import com.bit_zt.proj_socket.View.TitleBarLayout;

/**
 * Created by bit_zt on 15/12/9.
 */
public class AboutQchat extends Activity {


    private TitleBarLayout titleBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.aboutqchat);

        ActivityController.addActivity(this);

        inti();
    }

    private void inti() {

        titleBarLayout = (TitleBarLayout) findViewById(R.id.titleBarLayout_aboutQchat);
        titleBarLayout.tv_module.setText("About Qchat");

    }
}
