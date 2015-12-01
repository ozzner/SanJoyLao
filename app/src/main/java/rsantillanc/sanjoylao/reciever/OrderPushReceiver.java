package rsantillanc.sanjoylao.reciever;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.SJLApplication;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.ui.activity.OrderHistoryActivity;
import rsantillanc.sanjoylao.ui.mvp.OrderHistory.OrderHistoryPresenter;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by rsantillanc on 23/07/2015.
 */
public class OrderPushReceiver extends ParsePushBroadcastReceiver {

    private static final String TAG = OrderPushReceiver.class.getSimpleName() + Const.BLANK_SPACE;


    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);

        SJLApplication app = ((SJLApplication) context.getApplicationContext());
        Log.e(Const.DEBUG_PUSH, "Incoming push notification");

        if (intent == null)
            return;

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            String sOrder = json.get("order").toString();
            Gson gson = new Gson();
            OrderModel order = gson.fromJson(sOrder, OrderModel.class);

            Log.e(Const.DEBUG_PUSH, TAG + sOrder);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Const.EXTRA_ORDER, order);


            OrderHistoryActivity ac = new OrderHistoryActivity();
            OrderHistoryPresenter presenter = new OrderHistoryPresenter(context, ac);
            presenter.changeOrderStatus(order, context, Const.STATUS_CONFIRMED, app.getCurrentUser().getObjectId());


            /**Build PendingIntent*/

            Intent notifyIntent = new Intent(context, OrderHistoryActivity.class);
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            notifyIntent.putExtra(Const.KEY_PUSH_STATUS, Const.STATUS_CONFIRMED);
            notifyIntent.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            /**Build Notification*/

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder
                    .setSmallIcon(R.drawable.vec_notification_default)
                    .setContentTitle("Pedido en camino")
                    .setContentText("Order: aA23ferk")
                    .setTicker(context.getString(R.string.app_name))
                    .setLights(0xff00ff00, 300, 1000)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(soundUri);

            Notification notification = builder.getNotification();
            notification.flags = notification.flags | Notification.FLAG_SHOW_LIGHTS | Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(1, notification);

        } catch (JSONException e) {
            Log.e(Const.DEBUG_PUSH, "Push notification JSONException : " + e.getMessage());
        }
    }
}