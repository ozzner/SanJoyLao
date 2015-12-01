package rsantillanc.sanjoylao.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;

import java.security.MessageDigest;
import java.util.List;
import java.util.Locale;

/**
 * Created by RenzoD on 20/06/2015.
 */
public class Android {

    public static int getTypeDevice(Activity ctx) {
        int typeOfDevice = -1;

        DisplayMetrics metrics = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        double x = Math.pow(metrics.widthPixels / metrics.xdpi, 2);
        double y = Math.pow(metrics.heightPixels / metrics.ydpi, 2);
        double screenInches = Math.sqrt(x + y);

        screenInches = Math.round((double) Math.round(screenInches * 10) / 10);

        if (screenInches >= 10) {
            typeOfDevice = Const.TABLET_10;
        } else if (screenInches >= 7) {
            typeOfDevice = Const.TABLET_7;

        } else {
            typeOfDevice = Const.PHONE_SCREEN;
        }

        return typeOfDevice;
    }

    public static void setScreenOrientation(Activity ctx) {
        int type = Android.getTypeDevice(ctx);

        switch (type) {

            case Const.TABLET_10:
                ctx.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                break;

            case Const.TABLET_7:
                ctx.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                break;

        }
    }


    public static void genHashKey(Activity activity) {

        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo("rsantillanc.sanjoylao",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static String getAppVersion(Activity activity) {
        PackageInfo pInfo = null;
        try {
            pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;
        return version;
    }


    public static String getDeviceName(Activity activity) {

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String getLocalLanguage() {
        return  Locale.getDefault().getLanguage();
    }



    /**
     * Method checks if the app is in background or not
     *
     * @param context
     * @return
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

}
