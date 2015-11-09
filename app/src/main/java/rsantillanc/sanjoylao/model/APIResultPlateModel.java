package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import rsantillanc.sanjoylao.util.ConstAPI;

/**
 * Created by RenzoD on 01/11/2015.
 */
public class APIResultPlateModel {

    @SerializedName(ConstAPI.PARSE_KEY_RESULT)
    private ArrayList<RelationPlateSizeModel> resultArray;

    public ArrayList<RelationPlateSizeModel> getResultArray() {
        return resultArray;
    }

    public void setResultArray(ArrayList<RelationPlateSizeModel> resultArray) {
        this.resultArray = resultArray;
    }
}
