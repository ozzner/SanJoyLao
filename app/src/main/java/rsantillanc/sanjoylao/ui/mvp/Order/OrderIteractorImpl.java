package rsantillanc.sanjoylao.ui.mvp.Order;

import android.util.Log;

import com.google.gson.JsonObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rsantillanc.sanjoylao.api.service.ParseAPIService;
import rsantillanc.sanjoylao.model.BanquetModel;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.ConstAPI;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class OrderIteractorImpl implements IOrderIteractor {


    @Override
    public void getOrdersFromServer(String clausule, final OnOrdersListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    listener.onOrdersSuccess(new BanquetModel().dummyBanquets());
                } catch (InterruptedException e) {
                    listener.onOrdersError(e.getMessage());
                }
            }
        }).start();
    }


    public void createOrder() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<JsonObject> call = retrofit.create(ParseAPIService.class).createOrder(new OrderModel());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.isSuccess())
                    Log.e(Const.DEBUG, "Order created: " + response.body().toString());
                else
                    Log.e(Const.DEBUG, "Order error: " + response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(Const.DEBUG, "Order Throwable: " +t.getMessage());

            }
        });
    }
}
