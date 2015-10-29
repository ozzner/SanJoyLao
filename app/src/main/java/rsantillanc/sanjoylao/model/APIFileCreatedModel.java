package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsantillanc on 29/10/2015.
 */
public class APIFileCreatedModel {

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    public APIFileCreatedModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public APIFileCreatedModel(){

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
