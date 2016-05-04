package com.bit_zt.proj_socket.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by bit_zt on 15/9/10.
 */
public abstract class MyCommonAdapter<T> extends BaseAdapter{

    protected Context mContext;
    protected List<T> mdatalist;
    protected LayoutInflater inflater;

    //泛型构造，传入不同类型的数据bean
    public MyCommonAdapter(Context context, List<T> datalist) {
        this.mContext = context;
        this.mdatalist = datalist;
        inflater = LayoutInflater.from(context);
    }

    public void refresh(List<T> newDatalist){
        mdatalist = newDatalist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mdatalist.size();
    }

    @Override
    public Object getItem(int position) {
        return mdatalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //抽象getView
    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
