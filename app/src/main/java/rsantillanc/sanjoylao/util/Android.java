package rsantillanc.sanjoylao.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;

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


}
