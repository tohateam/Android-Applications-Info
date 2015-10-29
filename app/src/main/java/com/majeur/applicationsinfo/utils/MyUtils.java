package com.majeur.applicationsinfo.utils;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import java.util.*;

public class MyUtils
{
	// Kill running application
	public static void killApp(String processName, Activity activity) {
		ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
		manager.killBackgroundProcesses(processName);
	}
	
	// Is Running process
	public static boolean isProcessRunning(String processName, Activity activity) {
		if (processName == null) 
			return false;

		ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> processes = manager.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo process : processes) {
			if (processName.equals(process.processName)) {
				return true;
			}
		}
		return false;
	}

    public static boolean isFrozenApp(Context mContext, String mPackageName) {
        try {
            return (mContext.getPackageManager().getApplicationInfo(mPackageName, 0).enabled);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSystemApp(Context mContext, String mPackageName) {
        try {
            return (mContext.getPackageManager().getApplicationInfo(mPackageName, 0).flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
	
}
