package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import rsantillanc.sanjoylao.api.ConstAPI;

/**
 * Created by RenzoD on 01/11/2015.
 */
public class APIResultCategoryModel {

    @SerializedName(ConstAPI.PARSE_KEY_RESULT)
    private ArrayList<CategoryModel> resultArray;


    public ArrayList<CategoryModel> getResultArray() {
        return resultArray;
    }

    public void setResultArray(ArrayList<CategoryModel> resultArray) {
        this.resultArray = resultArray;
    }
}
