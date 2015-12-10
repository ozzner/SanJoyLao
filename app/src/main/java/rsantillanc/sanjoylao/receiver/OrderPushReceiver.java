package rsantillanc.sanjoylao.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.SJLApplication;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.ui.activity.OrderHistoryActivity;
import rsantillanc.sanjoylao.ui.mvp.OrderHistory.OrderHistoryIteractorImpl;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by rsantillanc on 23/07/2015.
 */
public class OrderPushReceiver extends ParsePushBroadcastReceiver {

    private static final String TAG = OrderPushReceiver.class.getSimpleName() + Const.BLANK_SPACE;
    private Intent historyIntent;
    private Context _context;
    private SJLApplication app;


    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);
        this._context = context;

        app = ((SJLApplication) context.getApplicationContext());
            Log.e(Const.DEBUG_PUSH, "Incoming push notification");

        if (intent == null)
            return;

        try {
            historyIntent = intent;
            //Get model
            OrderModel order = getModelFromJson(intent);
            Log.e(Const.DEBUG_PUSH, TAG + order.toString());

            //Set data & show
            buildDataToSend(order, context);

        } catch (JSONException e) {
            Log.e(Const.DEBUG_PUSH, "Push notification JSONException : " + e.getMessage());
        }
    }

    private void buildDataToSend(Serializable order, Context context) {
        Intent result = new Intent(context, OrderHistoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.EXTRA_ORDER, order);
        bundle.putInt(Const.KEY_PUSH_STATUS, Const.STATUS_CONFIRMED);
        historyIntent.putExtras(bundle);

        //Show
        showNotificationOrder(result,((OrderModel) order));
        changeStatusAtCurrentOrder(((OrderModel) order), app.getCurrentUser());
    }

    private void changeStatusAtCurrentOrder(OrderModel order, UserModel user) {
        OrderHistoryIteractorImpl impl = new OrderHistoryIteractorImpl();
        impl.updateOrder(_context, order, user.getObjectId(), Const.STATUS_CONFIRMED);
    }


    private void showNotificationOrder(Intent result, OrderModel order) {
        result.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        result.putExtras(historyIntent.getExtras());

        buildNotification(result,order);
    }

    private void buildNotification(Intent notifyIntent, OrderModel order) {
        if (Android.isAppIsInBackground(_context)) {

            /**Build PendingIntent*/
            PendingIntent pendingIntent = PendingIntent.getActivity(_context,
                    0, notifyIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            /**Build Notification*/
            Notification.Builder builder = new Notification.Builder(_context);
            builder.setSmallIcon(R.drawable.vec_notification_default);
            builder.setContentTitle(_context.getString(R.string.notification_order_confirmed));
            builder.setContentText("Order: " + order.getObjectId());
            builder.setSubText("Sr(a). "+ app.getCurrentUser().getFullName());
            builder.setTicker(_context.getString(R.string.app_name));
            builder.setLights(0xff00ff00, 300, 1000);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);

            //sound
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(soundUri);

            NotificationManager notificationManager = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                notificationManager.notify(10, builder.build());
            } else
                notificationManager.notify(10, builder.getNotification());

        } else {
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            _context.startActivity(notifyIntent);
        }
    }

    private OrderModel getModelFromJson(Intent intent) throws JSONException {
        JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
        String sOrder = json.get("order").toString();
        return new Gson().fromJson(sOrder, OrderModel.class);
    }
}