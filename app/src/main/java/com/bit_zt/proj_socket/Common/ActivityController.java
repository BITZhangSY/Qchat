package com.bit_zt.proj_socket.Common;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bit_zt on 15/12/9.
 */
public class ActivityController {

    public static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    public static void finishAll(){

        //结束所有已存在的Activity
        for(Activity activity : activityList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
