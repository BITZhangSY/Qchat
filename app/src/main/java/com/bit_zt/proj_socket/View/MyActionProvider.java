package com.bit_zt.proj_socket.View;

import android.content.Context;
import android.content.Intent;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.bit_zt.proj_socket.SubModuleActivity.AddFriends;
import com.bit_zt.proj_socket.SubModuleActivity.ChatInterface;
import com.bit_zt.proj_socket.R;

/**
 * Created by bit_zt on 15/11/26.
 */
public class MyActionProvider extends ActionProvider {

    private Context context;
    public MyActionProvider(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add(context.getString(R.string.menu_addfriend))
                .setIcon(R.drawable.menu_add_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Intent intent = new Intent(context, AddFriends.class);
                        context.startActivity(intent);

                        return true;
                    }
                });
        subMenu.add(context.getString(R.string.menu_group_chat))
                .setIcon(R.drawable.menu_group_chat_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Intent intent = new Intent(context, ChatInterface.class);
                        context.startActivity(intent);

                        return true;
                    }
                });


    }

    @Override
    public boolean hasSubMenu() {
        return true;
    }

}
