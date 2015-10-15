package rsantillanc.sanjoylao.ui.custom.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v4.app.DialogFragment;

import rsantillanc.sanjoylao.R;

/**
 * Created by RenzoD on 20/06/2015.
 */
public class SJLAlertDialog extends DialogFragment {

    private static OnBookingListener mBookingListener;
    private static String[] items;


    public SJLAlertDialog() {

    }

    public static void showTypeBookAlert(Context ctx, String title) {
        Resources src = ctx.getResources();
        items = src.getStringArray(R.array.bookings);

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder .setTitle(title)
                .setIcon(R.drawable.ic_importance_info)
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mBookingListener.onClick(dialog, which);
                    }
                })
                .setNeutralButton(src.getString(R.string.title_alert_yes),null);


        Dialog mDialog = builder.create();
        mDialog.show();

    }

    public void set(OnBookingListener listener) {
        this.mBookingListener = listener;
    }

    public interface OnBookingListener {
        void onClick(DialogInterface dialog, int index);
    }
}
