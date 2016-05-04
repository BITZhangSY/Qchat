package com.bit_zt.proj_socket.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.bit_zt.proj_socket.Common.BitmapUtils;
import com.bit_zt.proj_socket.Common.MyCommonAdapter;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.Common.ViewHolder;
import com.bit_zt.proj_socket.DataSet.Terminal;
import com.bit_zt.proj_socket.R;

import java.util.List;

/**
 * Created by bit_zt on 15/11/4.
 *
 * Adatper used to manage ChatList in MainActivity
 *
 */
public class Adapter_chatList extends MyCommonAdapter<Terminal> {

    public Adapter_chatList(Context context, List<Terminal> datalist) {
        super(context, datalist);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.getViewHolder(mContext,convertView,parent,
                R.layout.item_chat_list, position);

        Terminal terminal = mdatalist.get(position);

        viewHolder.setText(R.id.deviceName_chatList, terminal.getDeviceName());
        viewHolder.setText(R.id.ipAddress_chatList, terminal.getIpAddress());

        viewHolder.setImageFromDrawable(R.id.chatlist_headshow, Utils.user_headshow.get(terminal.getDeviceName()));

        return viewHolder.getConvertView();
    }
}
