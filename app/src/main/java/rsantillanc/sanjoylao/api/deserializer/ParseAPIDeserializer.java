package rsantillanc.sanjoylao.api.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.SizeModel;
import rsantillanc.sanjoylao.util.ConstAPI;

/**
 * Created by rsantillanc on 03/11/2015.
 */
public class ParseAPIDeserializer<T> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray array;
        if (typeOfT.equals(SizeModel.class)) {

            array = json.getAsJsonObject().getAsJsonArray(ConstAPI.PARSE_KEY_RESULT);
            return ((T) getModelsFromJsonArray(array, SizeModel.class));

        } else if (typeOfT.equals(PlateSizeModel.class)) {

            array = json.getAsJsonObject().getAsJsonArray(ConstAPI.PARSE_KEY_RESULT);
            return ((T) getModelsFromJsonArray(array, PlateSizeModel.class));

        } else
            return null;

    }

    private List<Object> getModelsFromJsonArray(JsonArray array, Type type) {
        return loopArray(array, new ArrayList(), new Gson(), type);
    }

    private List<Object> loopArray(JsonArray array, ArrayList list, Gson gson, Type type) {
        for (int i = 0; i < array.size(); i++) {
            Object item = gson.fromJson(array.get(i), type);
            list.add(item);
        }
        return list;
    }
}
