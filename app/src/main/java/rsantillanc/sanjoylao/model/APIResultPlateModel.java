package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import rsantillanc.sanjoylao.util.ConstAPI;

/**
 * Created by RenzoD on 01/11/2015.
 */
public class APIResultPlateModel {

    @SerializedName(ConstAPI.PARSE_KEY_RESULT)
    private ArrayList<PlateModel> resultArray;

    public ArrayList<PlateModel> getResultArray() {
        return resultArray;
    }

    public void setResultArray(ArrayList<PlateModel> resultArray) {
        this.resultArray = resultArray;
    }
}
