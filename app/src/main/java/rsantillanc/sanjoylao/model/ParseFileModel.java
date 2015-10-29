package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

import rsantillanc.sanjoylao.util.Const;

/**
 * Created by rsantillanc on 29/10/2015.
 */
public class ParseFileModel {

    private String __type ;

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    public ParseFileModel(String name, String url) {
        this.name = name;
        this.url = url;
        this.__type = Const.PARSE_TYPE_FILE;
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

