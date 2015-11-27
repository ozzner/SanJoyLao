package rsantillanc.sanjoylao.ui.mvp.PlateDetail;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
import rsantillanc.sanjoylao.model.ParsePointerModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.util.Const;
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
                if (response.isSuccess()) {
                    List<CommentModel> comments = (List<CommentModel>) response.body();

                    if (comments.isEmpty())
                        listener.onCommentEmpty();
                    else
                        listener.onCommentsLoadSuccess(comments);

                } else {
                    listener.onFailure("No hay datos.");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }

    private String makeJson(String idPlate) {
        return "{\"idPlate\":{\"__type\":\"Pointer\",\"className\":\"Plate\",\"objectId\":\"" + idPlate + "\"}}";
    }


//  {Helper methods}

    public Converter.Factory customConverter(Type type) {

        Gson build = new GsonBuilder()
                .registerTypeAdapter(type, new ParseAPIDeserializer<>())
                .create();
        return GsonConverterFactory.create(build);

    }

    private JsonObject makeJson(CharSequence idPlate, CharSequence idUser, String comment) {
        JsonObject body = new JsonObject();

        Gson gson = new Gson();
        JsonElement plate = gson.toJsonTree(new ParsePointerModel(Const.CLASS_PLATE, idPlate.toString()));
        JsonElement user = gson.toJsonTree(new ParsePointerModel(Const.CLASS_USER, idUser.toString()));

        body.addProperty("comment", comment);
        body.add("idPlate", plate);
        body.add("idUser", user);

        return body;
    }


    public void sendComment(final PlateModel plate, final UserModel user, final String comment, final OnPlateDetailListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<JsonObject> call = retrofit.create(ParseAPIService.class).newComment(makeJson(plate.getObjectId(), user.getObjectId(), comment));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.body() != null) {
                    Log.e(Const.DEBUG, "onResponse: " + response.body().toString());
                    listener.onCommentSendSuccess("Gracias.");
                    listener.addRecentlyComment(makeNewComment(response.body(), comment, user, plate));
                } else {
                    listener.onFailure("Se enviará más tarde.");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    private CommentModel makeNewComment(JsonObject body, String comment, UserModel user, PlateModel plate) {
        CommentModel obj = new CommentModel();
        obj.setUser(user);
        obj.setObjectId(body.get("objectId").getAsString());
        obj.setCreatedAt(body.get("createdAt").getAsString());
        obj.setComment(comment);
        obj.setPlate(plate);

        return obj;
    }
}
