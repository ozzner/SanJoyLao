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

import rsantillanc.sanjoylao.model.CategoryModel;

/**
 * Created by RenzoD on 21/10/2015.
 */
public class ParseDeserializer implements JsonDeserializer<List<CategoryModel>> {
    private Gson mGson;
    private List<CategoryModel> listCategory;

    @Override
    public List<CategoryModel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }


    private List<CategoryModel> getCategoryListFromJsonArray(JsonArray array) {
        listCategory = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            CategoryModel categoryItem = mGson.fromJson(array.toString(), CategoryModel.class);
            listCategory.add(categoryItem);
        }




        return listCategory;
    }



}
