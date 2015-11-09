package rsantillanc.sanjoylao.model;

import java.io.Serializable;

/**
 * Created by RenzoD on 03/06/2015.
 */
public class FeastModel implements Serializable {

    private static final long serialVersionUID = 0L;

    private String objectId;
    private ParseFileModel image;
    private String included;
    private String name;
    private boolean flagOptions;
    private double price;
    private String quantityPerson;
    private boolean status;


    public FeastModel(String objectId, ParseFileModel image, String included, String name, boolean flagOptions, double price, String quantityPerson, boolean status) {
        this.objectId = objectId;
        this.image = image;
        this.included = included;
        this.name = name;
        this.flagOptions = flagOptions;
        this.price = price;
        this.quantityPerson = quantityPerson;
        this.status = status;
    }





    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlagOptions() {
        return flagOptions;
    }

    public void setFlagOptions(boolean flagOptions) {
        this.flagOptions = flagOptions;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public ParseFileModel getImage() {
        return image;
    }

    public void setImage(ParseFileModel image) {
        this.image = image;
    }

    public String getIncluded() {
        return included;
    }

    public void setIncluded(String included) {
        this.included = included;
    }

    public String getQuantityPerson() {
        return quantityPerson;
    }

    public void setQuantityPerson(String quantityPerson) {
        this.quantityPerson = quantityPerson;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
