package rsantillanc.sanjoylao.api;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by RenzoD on 21/10/2015.
 */
    public class ParseDeserializer<T> implements JsonDeserializer<T>{
    private UserModel oUser;


    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (typeOfT == UserModel.class)
            {
                JsonObject response = json.getAsJsonObject().getAsJsonObject("updatedAt");

            }else{
                Log.e("Errorrrrrr", typeOfT.getClass().getSimpleName());
            }
        return null;
    }
}
