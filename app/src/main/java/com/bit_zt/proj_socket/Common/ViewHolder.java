package com.bit_zt.proj_socket.Common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by bit_zt on 15/9/10.
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private int mPostion;
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent, int layoutID, int position){
        this.mPostion = position;
        this.mViews = new SparseArray<View>();
        this.mConvertView = LayoutInflater.from(context).inflate(layoutID,parent,false);
        //将数据绑定到convertview
        this.mConvertView.setTag(this);
    }

        /*
        * 获取viewholder的静态方法，由类名调用
        * 注意！实质由convertview关联来找到viewholder
        * */
    public static ViewHolder getViewHolder(Context context, View convertView, ViewGroup parent,
                                int layoutID, int position){
        if(convertView == null){
            return new ViewHolder(context, parent, layoutID, position);
        }else{
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPostion = position;
            return viewHolder;
        }
    }

    //针对不同的控件   利用泛型管理
    public <T extends View> T getView(int viewID){
        View view = mViews.get(viewID);

        //若当前view不存在  则findviewbyid并存储到Array中
        if(view == null){
            view = mConvertView.findViewById(viewID);
            mViews.put(viewID,view);
        }

        //强制类型转换为T
        return (T)view;
    }

    public View getConvertView() {
        return mConvertView;
    }


    /*
    * 以下为增加的增加定制方法
    * */
    public ViewHolder setText(int viewID, String text){
        TextView tv = getView(viewID);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImageResource(int viewID, int picID){
        ImageView img = getView(viewID);
        img.setImageResource(picID);
        return this;
    }

    public ViewHolder setImageFromBitmap(int viewID, Bitmap bitmap){
        Drawable drawable = new BitmapDrawable(bitmap);
        return setImageFromDrawable(viewID, drawable);
    }

    public ViewHolder setImageFromDrawable(int viewID, Drawable drawable){
        ImageView img = getView(viewID);
        img.setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setTextColor(int viewID, int colorID){
        TextView tv = getView(viewID);
        tv.setTextColor(colorID);
        return this;
    }

    public ViewHolder setTextTypeFace(int viewID, Typeface typeface){
        TextView tv = getView(viewID);
        tv.setTypeface(typeface);
        return this;
    }

    public ViewHolder setVisibility(int viewID, int visibility_type){
        View view = getView(viewID);
        view.setVisibility(visibility_type);
        return this;
    }
}
