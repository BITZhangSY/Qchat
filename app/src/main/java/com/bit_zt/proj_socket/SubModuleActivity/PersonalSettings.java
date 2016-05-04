package com.bit_zt.proj_socket.SubModuleActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bit_zt.proj_socket.Common.ActivityController;
import com.bit_zt.proj_socket.Common.BitmapUtils;
import com.bit_zt.proj_socket.Common.MyApplication;
import com.bit_zt.proj_socket.Common.Utils;
import com.bit_zt.proj_socket.R;
import com.bit_zt.proj_socket.View.TitleBarLayout;

import java.io.File;

/**
 * Created by bit_zt on 15/11/9.
 */
public class PersonalSettings extends Activity {

    private Context context;
    private SharedPreferences preferences;

    /* View */
    private TitleBarLayout titleBarLayout;

    private RelativeLayout layout_change_headshow;
    private RelativeLayout layout_change_nickname;
    private RelativeLayout layout_change_password;
    private RelativeLayout layout_change_account;
    private RelativeLayout layout_change_sex;
    private RelativeLayout layout_change_location;
    private RelativeLayout layout_change_sign;

    private TextView nickname;
    private TextView account;
    private TextView sex;
    private TextView location;
    private TextView sign;

    private ImageView img_headshow;

    /** ImageView对象 */
    private ImageView personal_settings_img;
    private String[] items = new String[]{"选择本地图片", "拍照"};
    /** 头像名称 */
    private static final String IMAGE_FILE_NAME = "image.jpg";
    /** 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    private Drawable drawable=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.personal_settings);

        ActivityController.addActivity(this);

        initView();
        initEvents();
    }


    private void initEvents() {


        //修改头像
        layout_change_headshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        //修改昵称,密码,Q信号,地区,个性签名
        layout_change_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToChangeMyInfo("更改名字", "nickname", preferences.getString("nickname", ""), 10);
            }
        });
        layout_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToChangeMyInfo("更改密码", "password", preferences.getString("password", ""), 11);
            }
        });
        layout_change_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                jumpToChangeMyInfo("更改Q信号",2);
            }
        });
        layout_change_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToChangeMyInfo("更改地区","area", preferences.getString("location", ""),12);
            }
        });
        layout_change_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToChangeMyInfo("更改个性签名", "sign", preferences.getString("sign", ""), 4);
            }
        });

        layout_change_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("性别")
                        .setSingleChoiceItems(new String[] {"男","女"}, 0,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(0 == which){
                                            preferences.edit().putString("sex","男").commit();
                                            sex.setText("男");
                                        }else{
                                            preferences.edit().putString("sex","女").commit();
                                            sex.setText("女");
                                        }
                                        dialog.dismiss();
                                    }
                                }
                        )
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
    }

    private void initView() {

        context = this;
        preferences = Utils.getPreferences();

        img_headshow = (ImageView) findViewById(R.id.img_headshow);

        //获取sd卡中的头像图片
        Bitmap bitmap = BitmapUtils.getBitmap(BitmapUtils.headShowName);
        if(bitmap != null){
            Drawable drawable = new BitmapDrawable(this.getResources(),bitmap);
            img_headshow.setImageDrawable(drawable);
        }

        titleBarLayout = (TitleBarLayout) findViewById(R.id.titleBarLayout_personal);
        titleBarLayout.tv_module.setText("Personal");

        layout_change_headshow = (RelativeLayout) findViewById(R.id.layout_change_headshow);
        layout_change_nickname = (RelativeLayout) findViewById(R.id.layout_change_nickname);
        layout_change_password = (RelativeLayout) findViewById(R.id.layout_change_password);
        layout_change_account = (RelativeLayout) findViewById(R.id.layout_change_account);
        layout_change_sex = (RelativeLayout) findViewById(R.id.layout_change_sex);
        layout_change_location = (RelativeLayout) findViewById(R.id.layout_change_location);
        layout_change_sign = (RelativeLayout) findViewById(R.id.layout_change_sign);

        nickname = (TextView) findViewById(R.id.nickname);
        account = (TextView) findViewById(R.id.Qaccount);
        sex = (TextView) findViewById(R.id.sex);
        location = (TextView) findViewById(R.id.location);
        sign = (TextView) findViewById(R.id.sign);

        nickname.setText(preferences.getString("nickname",""));
        account.setText(preferences.getString("account",""));
        sex.setText(preferences.getString("sex",""));
        location.setText(preferences.getString("location","未填写"));
        sign.setText(preferences.getString("sign","未填写"));
    }

    private void jumpToChangeMyInfo(String content, String key,
                                    String defaultValue, int flag){
        Intent intent = new Intent(PersonalSettings.this,ChangeMyInfo.class);
        Bundle bundle = new Bundle();
        bundle.putString("title",content);
        bundle.putString("key",key);
        bundle.putString("deValue",defaultValue);
        bundle.putInt("flag", flag);
        intent.putExtras(bundle);
        startActivityForResult(intent, flag);
    }


    private void showDialog() {

        new AlertDialog.Builder(context)
                .setTitle("设置头像")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0 :
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery
                                        .setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery,
                                        IMAGE_REQUEST_CODE);
                                break;
                            case 1 :
                                Intent intentFromCapture = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                // 判断存储卡是否可以用，可用进行存储
                                String state = Environment
                                        .getExternalStorageState();
                                if (state.equals(Environment.MEDIA_MOUNTED)) {
                                    File path = Environment
                                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                                    File file = new File(path, IMAGE_FILE_NAME);
                                    intentFromCapture.putExtra(
                                            MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(file));
                                }

                                startActivityForResult(intentFromCapture,
                                        CAMERA_REQUEST_CODE);
                                break;

                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 结果码不等于取消时候

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE :
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE :
                    // 判断存储卡是否可以用，可用进行存储
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        File path = Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File tempFile = new File(path, IMAGE_FILE_NAME);
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(MyApplication.getContext(),
                                "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case RESULT_REQUEST_CODE : // 图片缩放完成后
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;

            }
        }else {
           switch (requestCode){
               case 10:
                   nickname.setText(preferences.getString("nickname",""));
                   break;
               case 11:
                   //密码不修改
                   break;
               case 12:
                   location.setText(preferences.getString("location",""));
               case 4:
                   sign.setText(preferences.getString("sign",""));
                   break;
           }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            BitmapUtils.saveBitmap(photo,BitmapUtils.headShowName);

            drawable = new BitmapDrawable(this.getResources(), photo);

            img_headshow.setImageDrawable(drawable);
        }
    }
}
