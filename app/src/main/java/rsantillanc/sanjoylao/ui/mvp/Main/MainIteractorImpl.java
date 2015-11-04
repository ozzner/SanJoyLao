package rsantillanc.sanjoylao.ui.mvp.Main;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

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
import rsantillanc.sanjoylao.util.ConstAPI;
import rsantillanc.sanjoylao.api.service.ParseAPIService;
import rsantillanc.sanjoylao.api.deserializer.ParseAPIDeserializer;
import rsantillanc.sanjoylao.model.APIResultCategoryModel;
import rsantillanc.sanjoylao.model.CategoryModel;
import rsantillanc.sanjoylao.model.SizeModel;
import rsantillanc.sanjoylao.storage.dao.CategoryDao;
import rsantillanc.sanjoylao.storage.dao.SizeDao;
import rsantillanc.sanjoylao.util.Const;

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


        if (countSizes(ctx) == 0){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstAPI.PARSE_URL_BASE)
                    .addConverterFactory(myConverter())
                    .build();

            Call<SizeModel> request = retrofit.create(ParseAPIService.class).getSizes();
            request.enqueue(new Callback<SizeModel>() {
                @Override
                public void onResponse(Response<SizeModel> response, Retrofit retrofit) {

                    if (response.isSuccess()){
                        List<SizeModel> list = new ArrayList();
                        list.addAll((Collection<? extends SizeModel>) response.body());

                        long rows = 0;
                        for (SizeModel sizeModel : list) {
                             rows = new SizeDao(ctx).insert(sizeModel);
                        }
                        Log.e(Const.DEBUG, "rows affected: " + rows);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(Const.DEBUG, "onFailure: " ,t);
                }
            });
        }else{
            Log.e(Const.DEBUG, "hay data sizeModel ");
        }
    }

    public Converter.Factory myConverter() {

        Gson build = new GsonBuilder()
                .registerTypeAdapter(SizeModel.class,new ParseAPIDeserializer<>())
                .create();
        return GsonConverterFactory.create(build);
    }
//    }


    protected int countCategories(Context c) {
        return new CategoryDao(c).count();
    }

    protected int countSizes(Context c) {
        return new SizeDao(c).count();
    }

    public void getProfileImage(Context c, ImageView imageView, String url) {
        Picasso.with(c)
                .load(url)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(imageView);
    }


}
