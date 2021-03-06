package rsantillanc.sanjoylao.ui.mvp.Main;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Converter;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.api.deserializer.ParseAPIDeserializer;
import rsantillanc.sanjoylao.api.service.ParseAPIService;
import rsantillanc.sanjoylao.model.APIResultCategoryModel;
import rsantillanc.sanjoylao.model.CategoryModel;
import rsantillanc.sanjoylao.model.LocalRestaurantModel;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.OrderTypeModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.RelationLocalRestaurant;
import rsantillanc.sanjoylao.model.RestaurantModel;
import rsantillanc.sanjoylao.model.SizeModel;
import rsantillanc.sanjoylao.model.StatusModel;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.storage.dao.CategoryDao;
import rsantillanc.sanjoylao.storage.dao.LocalRestaurantDao;
import rsantillanc.sanjoylao.storage.dao.OrderDao;
import rsantillanc.sanjoylao.storage.dao.OrderTypeDao;
import rsantillanc.sanjoylao.storage.dao.PlateDao;
import rsantillanc.sanjoylao.storage.dao.PlateSizeDao;
import rsantillanc.sanjoylao.storage.dao.RestaurantDao;
import rsantillanc.sanjoylao.storage.dao.SizeDao;
import rsantillanc.sanjoylao.storage.dao.StatusDao;
import rsantillanc.sanjoylao.storage.dao.UserDao;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.ConstAPI;
import rsantillanc.sanjoylao.util.SJLStrings;

/**
 * Created by rsantillanc on 20/10/2015.
 */
public class MainIteractorImpl {

    public void syncCategories(final Context c) {

        if (countCategories(c) != 12) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstAPI.PARSE_URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Call<APIResultCategoryModel> call = retrofit.create(ParseAPIService.class).getCategories();
            call.enqueue(new Callback<APIResultCategoryModel>() {
                @Override
                public void onResponse(Response<APIResultCategoryModel> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        long rows = 0;
                        for (CategoryModel categoryModel : response.body().getResultArray()) {
                            rows = new CategoryDao(c).insert(categoryModel);
                        }
                        Log.e(Const.DEBUG, "Response rows inserted: " + rows);

                    } else {
                        Log.e(Const.DEBUG, "error response: " + response.body());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(Const.DEBUG, "onFailure: " + t.getMessage());

                }
            });
        } else {
//            Log.e(Const.DEBUG, "Igual a 12: " +countCategories(c));
        }
    }


    public void syncSizes(final Context ctx) {


        if (countSizes(ctx) == 0) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstAPI.PARSE_URL_BASE)
                    .addConverterFactory(myConverter(SizeModel.class))
                    .build();

            Call<SizeModel> request = retrofit.create(ParseAPIService.class).getAllSizes();
            request.enqueue(new Callback<SizeModel>() {
                @Override
                public void onResponse(Response<SizeModel> response, Retrofit retrofit) {

                    if (response.isSuccess()) {
                        List<SizeModel> list = new ArrayList();
                        list.addAll((Collection<? extends SizeModel>) response.body());

                        long rows = 0;
                        for (SizeModel sizeModel : list) {
                            rows = new SizeDao(ctx).insert(sizeModel);
                        }
                        Log.e(Const.DEBUG, "size rows affected: " + rows);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(Const.DEBUG, "onFailure: ", t);
                }
            });
        } else {
            Log.e(Const.DEBUG, "hay data sizeModel ");
        }
    }


    public void syncPlateSize(final Context ctx) {

        if (countPlatesSizes(ctx) == 0) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstAPI.PARSE_URL_BASE)
                    .addConverterFactory(myConverter(PlateSizeModel.class))
                    .build();

            Call<PlateSizeModel> request = retrofit.create(ParseAPIService.class).getAllPlatesSize();
            request.enqueue(new Callback<PlateSizeModel>() {
                @Override
                public void onResponse(Response<PlateSizeModel> response, Retrofit retrofit) {

                    if (response.isSuccess()) {
                        List<PlateSizeModel> list = new ArrayList();
                        list.addAll((Collection<? extends PlateSizeModel>) response.body());

                        long rows = 0;
                        for (PlateSizeModel plateSize : list) {
                            rows = new PlateSizeDao(ctx).insert(plateSize);
                        }
                        Log.e(Const.DEBUG, "plateSize rows affected: " + rows);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(Const.DEBUG, "onFailure: ", t);
                }
            });
        } else {
            Log.e(Const.DEBUG, "hay data sizeModel ");
        }
    }


    public void syncPlates(final Context c) {

        if (countPlates(c) == 0) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstAPI.PARSE_URL_BASE)
                    .addConverterFactory(myConverter(PlateModel.class))
                    .build();

            Call<PlateModel> request = retrofit.create(ParseAPIService.class).getAllPlates();
            request.enqueue(new Callback<PlateModel>() {
                @Override
                public void onResponse(Response<PlateModel> response, Retrofit retrofit) {

                    if (response.isSuccess()) {
                        List<PlateModel> list = new ArrayList();
                        list.addAll((Collection<? extends PlateModel>) response.body());

                        long rows = 0;
                        for (PlateModel plate : list) {
                            rows = new PlateDao(c).insert(plate);
                        }
                        Log.e(Const.DEBUG, "plate rows affected: " + rows);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(Const.DEBUG, "onFailure: ", t);
                }
            });
        } else {
            Log.e(Const.DEBUG, "Ya hay platos ");
        }
    }

    public void syncOrderType(final Context c) {

        if (countOrderType(c) == 0) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstAPI.PARSE_URL_BASE)
                    .addConverterFactory(myConverter(OrderTypeModel.class))
                    .build();

            //Make call
            Call<OrderTypeModel> call = retrofit.create(ParseAPIService.class).getAllOrderTypes();
            call.enqueue(new Callback<OrderTypeModel>() {
                @Override
                public void onResponse(Response<OrderTypeModel> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        List<OrderTypeModel> list = new ArrayList();
                        list.addAll((Collection<? extends OrderTypeModel>) response.body());

                        long rows = 0;
                        for (OrderTypeModel orderType : list) {
                            rows = new OrderTypeDao(c).insert(orderType);
                        }
                        Log.e(Const.DEBUG, "orderType rows affected: " + rows);
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });


        } else {
            //TODO PUEDE SACAR DESDE LA BASE DE DATOS LOCAL.
        }
    }


    public void syncStatus(final Context c) {

        if (countStatus(c) == 0) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstAPI.PARSE_URL_BASE)
                    .addConverterFactory(myConverter(StatusModel.class))
                    .build();

            Call<StatusModel> call = retrofit.create(ParseAPIService.class).getAllStatus();
            call.enqueue(new Callback<StatusModel>() {
                @Override
                public void onResponse(Response<StatusModel> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        List<StatusModel> list = new ArrayList();
                        list.addAll((Collection<? extends StatusModel>) response.body());

                        long rows = 0;
                        for (StatusModel status : list) {
                            rows = new StatusDao(c).insert(status);
                        }
                        Log.e(Const.DEBUG, "status rows affected: " + rows);
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });

        } else {
            //TODO PUEDE SACAR DESDE LA BASE DE DATOS LOCAL.
        }
    }


    public void syncOrders(final Context c, final String userID) {
        if (countOrders(c) == 0) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstAPI.PARSE_URL_BASE)
                    .addConverterFactory(myConverter(OrderModel.class))
                    .build();

            Call<OrderModel> call = retrofit.create(ParseAPIService.class).getAllOrders(SJLStrings.getUrlEncoded(makeJsonFilter(userID)));
            call.enqueue(new Callback<OrderModel>() {
                @Override
                public void onResponse(Response<OrderModel> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        List<OrderModel> list = new ArrayList();
                        list.addAll((Collection<? extends OrderModel>) response.body());

                        long rows = 0;
                        for (OrderModel order : list) {
                            rows = new OrderDao(c).insert(order);

//                            if (order.getStatus().getCode() == Const.STATUS_CODE_TEMPORAL){
//
//                            }

                        }
                        Log.e(Const.DEBUG, "orders rows affected: " + rows);
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });

        } else {
            //TODO PUEDE SACAR DESDE LA BASE DE DATOS LOCAL.
        }

    }


    public void syncRestaurants(final Context c) {
        if (countRestaurants(c) == 0) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstAPI.PARSE_URL_BASE)
                    .addConverterFactory(myConverter(RestaurantModel.class))
                    .build();

            Call<RestaurantModel> call = retrofit.create(ParseAPIService.class).getAllRestaurants();
            call.enqueue(new Callback<RestaurantModel>() {
                @Override
                public void onResponse(Response<RestaurantModel> response, Retrofit retrofit) {
                    if (response.isSuccess() && response.body() != null) {
                        List<RestaurantModel> restaurants = (List<RestaurantModel>) response.body();
                        for (RestaurantModel restaurant : restaurants) {
                            long insert = new RestaurantDao(c).insert(restaurant);
                            Log.e(Const.DEBUG, "Restaurants rows affected: " + insert);
                        }
                    } else
                        Log.e(Const.DEBUG, "Restaurants error load: " + response.message());

                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(Const.DEBUG, "Restaurants onFailure: ");
                }
            });
        }


    }


    public void syncLocals(final Context c) {
        if (countLocalRestaurants(c) == 0) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstAPI.PARSE_URL_BASE)
                    .addConverterFactory(myConverter(LocalRestaurantModel.class))
                    .build();


            Call<LocalRestaurantModel> call = retrofit.create(ParseAPIService.class).getAllLocalRestaurants();
            call.enqueue(new Callback<LocalRestaurantModel>() {
                @Override
                public void onResponse(Response<LocalRestaurantModel> response, Retrofit retrofit) {
                    if (response.isSuccess() && response.body() != null) {
                        RelationLocalRestaurant relation = new RelationLocalRestaurant();
                        relation.setLocals((List<LocalRestaurantModel>) response.body());

                        for (LocalRestaurantModel local : relation.getLocals()) {
                            long insert = new LocalRestaurantDao(c).insert(local);
                            Log.e(Const.DEBUG, "LocalRestaurant rows affected: " + insert);
                        }

                    } else
                        Log.e(Const.DEBUG, "LocalRestaurant error: " + response.message());

                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(Const.DEBUG, "Restaurants onFailure: " + t.getMessage());
                }
            });

        }

    }

    private int countLocalRestaurants(Context c) {
        return countLocals(c);
    }


    //--------------------{COUNTERS}--------------------

    private int countLocals(Context c) {
        return new LocalRestaurantDao(c).count();
    }

    private int countRestaurants(Context c) {
        return new RestaurantDao(c).count();
    }

    private int countStatus(Context c) {
        return new StatusDao(c).count();
    }

    private int countOrderType(Context c) {
        return new OrderTypeDao(c).count();
    }

    private int countPlates(Context c) {
        return new PlateDao(c).count();
    }

    private int countOrders(Context c) {
        return new OrderDao(c).count();
    }

    public Converter.Factory myConverter(Type type) {

        Gson build = new GsonBuilder()
                .registerTypeAdapter(type, new ParseAPIDeserializer<>())
                .create();
        return GsonConverterFactory.create(build);
    }

    protected int countCategories(Context c) {
        return new CategoryDao(c).count();
    }

    protected int countSizes(Context c) {
        return new SizeDao(c).count();
    }

    protected int countPlatesSizes(Context c) {
        return new PlateSizeDao(c).count();
    }


    //{Custom}
    public void getProfileImage(Context c, ImageView imageView, String url) {
        Picasso.with(c)
                .load(url)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(imageView);
    }

    private String makeJsonFilter(String userID) {
        String s = "{\"idUser\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"" + userID + "\"}}";
        return s;
    }

    public void subscriberMe(String objectId) {
        ParsePush.subscribeInBackground(Const.SJL_CHANNEL_CLIENT + objectId);
    }

    public void closeSession(Activity mView, UserModel currentUser) {
        new UserDao(mView).logout();
        List<String> subscribedChannels = ParseInstallation.getCurrentInstallation().getList("channels");
        for (int i = 0; i < subscribedChannels.size(); i++) {
            ParsePush.unsubscribeInBackground(subscribedChannels.get(i));
        }

    }
}
