package com.example.activitytest;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import static android.os.Process.killProcess;

/**
 * Created by els on 17-3-4.
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }
    public static void finishAll() {
        for (Activity activity: activities) {
            if (!activity.isFinishing()) {
                activity.finish();
//                killProcess(android.os.Process.myPid());
            }
        }
    }
}
