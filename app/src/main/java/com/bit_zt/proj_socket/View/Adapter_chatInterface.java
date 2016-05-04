package com.bit_zt.proj_socket.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import com.bit_zt.proj_socket.Common.BitmapUtils;
import com.bit_zt.proj_socket.Common.MyCommonAdapter;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.Common.ViewHolder;
import com.bit_zt.proj_socket.DataSet.ChatMsgEntity;
import com.bit_zt.proj_socket.R;

import java.util.List;

/**
 * Created by bit_zt on 15/11/7.
 */
public class Adapter_chatInterface extends MyCommonAdapter<ChatMsgEntity> {

    public static interface MsgType{
        int MSG_MINE = 1;
        int MSG_NOT_MINE = 0;
    }

    public Adapter_chatInterface(Context context, List<ChatMsgEntity> datalist) {
        super(context, datalist);
    }

    /*
    *  1.每次即将加载一个item系统就会调用此方法判断其类型，根据返回的类型区缓冲区中查找相应的视图，
    *    如果能找到，那么复用；否则，没有复用。
    *  2.每当一个item显示完滑出listView，那么系统同样调用此方法判断其视图类型，如果缓冲区中存在该复用视图，
    *    那么直接移除该item；否则，加入缓冲区用于复用。
    * */
    @Override
    public int getItemViewType(int position) {
        ChatMsgEntity chatMsgEntity = mdatalist.get(position);


        if(chatMsgEntity.getMsgType()){
            return MsgType.MSG_MINE;
        }else {
            return MsgType.MSG_NOT_MINE;
        }
    }

    /*
    *   给ListView设置适配器的时候，在内存中开辟一块复用缓冲区-数组，数组的长度就是这个方法的返回值。
    * */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        ChatMsgEntity chatMsgEntity = mdatalist.get(position);

        //满足不同的条件提供相应视图
        if(chatMsgEntity.getMsgType()){
            viewHolder = ViewHolder.getViewHolder(mContext,convertView,parent,
                    R.layout.chatting_item_msg_text_right,position);
            Bitmap bitmap = BitmapUtils.getBitmap(BitmapUtils.headShowName);
            viewHolder.setImageFromBitmap(R.id.img_myheadshow_right,bitmap);
        }else{
            viewHolder = ViewHolder.getViewHolder(mContext,convertView,parent,
                    R.layout.chatting_item_msg_text_left,position);
            viewHolder.setImageFromDrawable(R.id.img_myheadshow_left, Utils.user_headshow.get(chatMsgEntity.getName()));
        }

        viewHolder.setText(R.id.tv_username, chatMsgEntity.getName());
        viewHolder.setText(R.id.tv_chatcontent, chatMsgEntity.getText());
        viewHolder.setText(R.id.tv_sendtime, chatMsgEntity.getDate());


        return viewHolder.getConvertView();
    }
}
