package rsantillanc.sanjoylao.ui.mvp.PlateDetail;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Converter;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rsantillanc.sanjoylao.api.deserializer.ParseAPIDeserializer;
import rsantillanc.sanjoylao.api.service.ParseAPIService;
import rsantillanc.sanjoylao.model.CommentModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.util.ConstAPI;
import rsantillanc.sanjoylao.util.SJLStrings;

/**
 * Created by RenzoD on 13/11/2015.
 */
public class PlateDetailIteractorImpl {
    public void loadCommentsByPlateId(Context c, PlateModel plate, final OnPlateDetailListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(customConverter(CommentModel.class))
                .build();

        Call<CommentModel> call = retrofit.create(ParseAPIService.class).findCommentsBy(SJLStrings.getUrlEncoded(makeJson(plate.getObjectId())));
        call.enqueue(new Callback<CommentModel>() {
            @Override
            public void onResponse(Response<CommentModel> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    listener.onCommentsLoadSuccess((List<CommentModel>) response.body());
                }else {
                    listener.onFailure("No hay datos.");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }



//  {Helper methods}

    public Converter.Factory customConverter(Type type) {

        Gson build = new GsonBuilder()
                .registerTypeAdapter(type, new ParseAPIDeserializer<>())
                .create();
        return GsonConverterFactory.create(build);

    }

    private String makeJson(CharSequence idPlate) {
        return "{\"idPlate\":{\"__type\":\"Pointer\",\"className\":\"Plate\",\"objectId\":\"" + idPlate + "\"}}";
    }


}
