package rsantillanc.sanjoylao.ui.custom.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v4.app.DialogFragment;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.util.OnPositiveListener;

/**
 * Created by RenzoD on 20/06/2015.
 */
public class SJLAlertDialog extends DialogFragment {

    private static OnSJLAlertDialogListener mBookingListener;
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

    public void set(OnSJLAlertDialogListener listener) {
        this.mBookingListener = listener;
    }

    public static void showCustomAlert(Context ctx,PlateModel plate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(plate.getIngredients());
        builder.setTitle(plate.getName());
        Dialog dialog = builder.create();
        dialog.show();
    }

    public static void showWarningAlert(Activity act, final OnPositiveListener positive,CharSequence message) {
        Resources src = act.getResources();

        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder .setTitle(act.getString(R.string.warning))
                .setMessage(message)
                .setPositiveButton(act.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        positive.positiveClick();
                    }
                })
                .setNegativeButton(act.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });




        Dialog mDialog = builder.create();
        mDialog.show();
    }

    public interface OnSJLAlertDialogListener {
        void onClick(DialogInterface dialog, int index);
    }


}
