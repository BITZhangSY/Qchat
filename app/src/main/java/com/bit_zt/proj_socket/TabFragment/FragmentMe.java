package com.bit_zt.proj_socket.TabFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bit_zt.proj_socket.SubModuleActivity.AboutQchat;
import com.bit_zt.proj_socket.Common.ActivityController;
import com.bit_zt.proj_socket.Common.BitmapUtils;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.SubModuleActivity.PersonalSettings;
import com.bit_zt.proj_socket.R;

/**
 * Created by bit_zt on 15/11/8.
 */
public class FragmentMe extends Fragment {

    private View rootview;

    private RelativeLayout layout_personal_settings;
    private RelativeLayout layout_about;
    private RelativeLayout layout_change_quit;

    private ImageView headshow;
    private TextView nickname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.tab_fragment_me,container,false);

        initView();

        return rootview;
    }

    @Override
      public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvents();
    }

    private void initView() {

        layout_personal_settings = (RelativeLayout) rootview.findViewById(R.id.layout_personal_settings);
        layout_about = (RelativeLayout) rootview.findViewById(R.id.layout_about);
        layout_change_quit = (RelativeLayout) rootview.findViewById(R.id.layout_change_quit);
        headshow = (ImageView) rootview.findViewById(R.id.headshow);
        setHeadshow();

        nickname = (TextView) rootview.findViewById(R.id.nickname);
        nickname.setText(Utils.getPreferences().getString("nickname",""));
        if(nickname.getText().toString().equals("")){
            nickname.setText("暂无昵称");
        }
    }


    private void initEvents(){

        layout_personal_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), PersonalSettings.class);
                startActivity(intent);
            }
        });

        layout_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AboutQchat.class);
                startActivity(intent);
            }
        });

        layout_change_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改登录状态,下次需要登录
                Utils.getPreferences().edit().putBoolean("isLogin",false).commit();
                ActivityController.finishAll();
            }
        });
    }

    private void setHeadshow(){
        Bitmap bitmap = BitmapUtils.getBitmap(BitmapUtils.headShowName);
        if(bitmap != null){
            Drawable drawable = new BitmapDrawable(getResources(),bitmap);
            headshow.setImageDrawable(drawable);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        //当从PersonalSettings切换回来时更新
        setHeadshow();
    }
}
