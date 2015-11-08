package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsantillanc on 29/10/2015.
 */
public class APIResponseFileModel {

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    public APIResponseFileModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public APIResponseFileModel(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
