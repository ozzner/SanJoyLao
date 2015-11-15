package rsantillanc.sanjoylao.api.deserializer;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rsantillanc.sanjoylao.model.CommentModel;
import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.OrderTypeModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.RelationPlateSizeModel;
import rsantillanc.sanjoylao.model.SizeModel;
import rsantillanc.sanjoylao.model.StatusModel;
import rsantillanc.sanjoylao.ui.mvp.Plate.PlateIteractorImpl;
import rsantillanc.sanjoylao.util.ConstAPI;

/**
 * Created by rsantillanc on 03/11/2015.
 */
public class ParseAPIDeserializer<T> implements JsonDeserializer<T> {

    Context _context;

    public Context get_context() {
        return _context;
    }

    public void set_context(Context _context) {
        this._context = _context;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray array;

        //SizeModel
        if (typeOfT.equals(SizeModel.class)) {

            array = json.getAsJsonObject().getAsJsonArray(ConstAPI.PARSE_KEY_RESULT);
            return ((T) getModelsFromJsonArray(array, SizeModel.class));

            //PlateSizeModel
        } else if (typeOfT.equals(PlateSizeModel.class)) {

            array = json.getAsJsonObject().getAsJsonArray(ConstAPI.PARSE_KEY_RESULT);
            return ((T) getModelsFromJsonArray(array, PlateSizeModel.class));

            //PlateModel
        } else if (typeOfT.equals(PlateModel.class)) {

            array = json.getAsJsonObject().getAsJsonArray(ConstAPI.PARSE_KEY_RESULT);
            return ((T) getModelsFromJsonArray(array, PlateModel.class));

            //OrderTypeModel
        } else if (typeOfT.equals(OrderTypeModel.class)) {

            array = json.getAsJsonObject().getAsJsonArray(ConstAPI.PARSE_KEY_RESULT);
            return ((T) getModelsFromJsonArray(array, OrderTypeModel.class));

            //StatusModel
        } else if (typeOfT.equals(StatusModel.class)) {

            array = json.getAsJsonObject().getAsJsonArray(ConstAPI.PARSE_KEY_RESULT);
            return ((T) getModelsFromJsonArray(array, StatusModel.class));

           //OrderDetailModel
        } else if (typeOfT.equals(OrderDetailModel.class)) {

            array = json.getAsJsonObject().getAsJsonArray(ConstAPI.PARSE_KEY_RESULT);
            return ((T) getModelsFromJsonArray(array, OrderDetailModel.class));

            //OrderModel
        }else if (typeOfT.equals(OrderModel.class)) {

            array = json.getAsJsonObject().getAsJsonArray(ConstAPI.PARSE_KEY_RESULT);
            return ((T) getModelsFromJsonArray(array, OrderModel.class));

            //CommentModel
        }else if (typeOfT.equals(CommentModel.class)) {

            array = json.getAsJsonObject().getAsJsonArray(ConstAPI.PARSE_KEY_RESULT);
            return ((T) getModelsFromJsonArray(array, CommentModel.class));

        }else
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

    private List<Object> buildPlateSizeRelation(JsonArray array, ArrayList list, Gson gson, Type type) {
        RelationPlateSizeModel relation = new RelationPlateSizeModel();
        PlateIteractorImpl iteractor = new PlateIteractorImpl(_context);

        for (int i = 0; i < array.size(); i++) {
            PlateModel plate = gson.fromJson(array.get(i), type);
            String objectId = plate.getObjectId();
            relation.setCurrentPlate(plate);
            relation.setListSizes(iteractor.getSizesByPlateID(objectId));

            list.add(relation);
        }

        return list;
    }
}
