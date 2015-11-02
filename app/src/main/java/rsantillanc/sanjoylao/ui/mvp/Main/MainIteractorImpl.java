package rsantillanc.sanjoylao.ui.mvp.Main;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.api.ConstAPI;
import rsantillanc.sanjoylao.api.ParseAPIService;
import rsantillanc.sanjoylao.model.APIResultCategoryModel;
import rsantillanc.sanjoylao.model.CategoryModel;
import rsantillanc.sanjoylao.storage.dao.CategoryDao;
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
        }else {
//            Log.e(Const.DEBUG, "Igual a 12: " +countCategories(c));
        }


    }


    protected int countCategories(Context c) {
        return new CategoryDao(c).count();
    }

    public void getProfileImage(Context c,ImageView imageView,String url) {
        Picasso.with(c)
                .load(url)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(imageView);
    }

//    public GsonConverterFactory buildAPIGsonConverter(){
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(CategoryModel.class,new ParseDeserializer<CategoryModel>())
//                .create();
//        return GsonConverterFactory.create(gson);
//    }

}
