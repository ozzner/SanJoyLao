package rsantillanc.sanjoylao.model;

/**
 * Created by RenzoD on 04/12/2015.
 */
public class RestaurantModel extends BaseModel {

    private String about;
    private long centralCallNumber;
    private String name;
    private long ruc;
    private String slogan;


    public RestaurantModel() {
    }

    public RestaurantModel(String about, long centralCallNumber, String name, long ruc, String slogan) {
        this.about = about;
        this.centralCallNumber = centralCallNumber;
        this.name = name;
        this.ruc = ruc;
        this.slogan = slogan;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public long getCentralCallNumber() {
        return centralCallNumber;
    }

    public void setCentralCallNumber(long centralCallNumber) {
        this.centralCallNumber = centralCallNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRuc() {
        return ruc;
    }

    public void setRuc(long ruc) {
        this.ruc = ruc;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}
