package rsantillanc.sanjoylao.model;

/**
 * Created by rsantillanc on 29/10/2015.
 */
public class ParseDateModel {

    private String __type;
    private String iso;

    public ParseDateModel(String __type, String iso) {
        this.__type = __type;
        this.iso = iso;
    }


    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }
}
