package com.bit_zt.proj_socket.Common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bit_zt on 15/11/9.
 */
public class BitmapUtils {

    private static String sdCardCameraPath = "/sdcard/DCIM/Camera/";
    public static String headShowName = "QchatHeadshow";

    public static Bitmap getBitmap(String fileName){
        fileName += ".png";
        String filepath = sdCardCameraPath + fileName;
        Bitmap bitmap = BitmapFactory.decodeFile(filepath,null);

        return bitmap;
    }

    public static Drawable getDrawble(String fileName){
        Bitmap bitmap = getBitmap(fileName);

        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    public static void saveBitmap(Bitmap bitmap,String fileName)
    {
        fileName += ".png";
        File file = new File(sdCardCameraPath + fileName);
        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 90, out))
            {
                out.flush();
                out.close();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 将照片存储为字符串形式(经过BASE64编码)
     */
    public static String DrawableToString(Drawable drawable) {
        if(drawable == null)
        {
            return "";
        }
        BitmapDrawable bd = (BitmapDrawable)drawable;
        Bitmap bmp = bd.getBitmap();
        if(bmp == null)
            return "";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //压缩图片
        bmp.compress(Bitmap.CompressFormat.PNG, 60, stream);
        byte[] b = stream.toByteArray();
        // 将图片流以字符串形式存储下来
        return Base64Coder.encodeLines(b);
    }

    /**
     * 将Base64Coder编码后的字符串转换为Drawable
     */
    public static Drawable StringToDrawable(String encodeStr) {
        if(encodeStr==null || encodeStr.isEmpty())
        {
            return null;
        }
        //Base64Coder解码
        ByteArrayInputStream in = new ByteArrayInputStream(Base64Coder.decodeLines(encodeStr));
        Bitmap dBitmap = BitmapFactory.decodeStream(in);
        Drawable drawable = new BitmapDrawable(dBitmap);
        return drawable;
    }
}
