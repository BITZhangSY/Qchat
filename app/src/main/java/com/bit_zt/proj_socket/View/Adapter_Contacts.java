package com.bit_zt.proj_socket.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.bit_zt.proj_socket.Common.MyCommonAdapter;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.Common.ViewHolder;
import com.bit_zt.proj_socket.DataSet.ContactsEntity;
import com.bit_zt.proj_socket.R;

import java.util.List;

/**
 * Created by bit_zt on 15/11/11.
 */
public class Adapter_Contacts extends MyCommonAdapter<ContactsEntity> {


    public Adapter_Contacts(Context context, List<ContactsEntity> datalist) {
        super(context, datalist);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.getViewHolder(mContext,convertView,parent,
                R.layout.item_contact_list, position);

        ContactsEntity contactsEntity = mdatalist.get(position);

        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if(position == getPositionForSection(section)){
            viewHolder.setVisibility(R.id.contacts_catalogLayout, View.VISIBLE);
            viewHolder.setText(R.id.contacts_catalog,contactsEntity.getSortLetter());
        }else {
            viewHolder.setVisibility(R.id.contacts_catalogLayout, View.GONE);
        }

        viewHolder.setText(R.id.contacts_friend, contactsEntity.getuserAccount());

        Drawable drawable = Utils.user_headshow.get(contactsEntity.getDeviceName());
        if(drawable != null){
            viewHolder.setImageFromDrawable(R.id.contacts_photo, drawable);
        }else{
            viewHolder.setImageResource(R.id.contacts_photo, R.drawable.mini_avatar_shadow);
        }

        return viewHolder.getConvertView();
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return mdatalist.get(position).getSortLetter().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mdatalist.get(i).getSortLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}
