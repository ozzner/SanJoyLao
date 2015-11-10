package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rsantillanc on 03/11/2015.
 */
public class PlateSizeModel implements Serializable {
    private static final long serialVersionUID = 0L;

    private String objectId;
    @SerializedName("idPlate")
    private PlateModel plate;
    @SerializedName("idSize")
    private SizeModel size;
    private double price;
    private int timeOfPreparation;


    public PlateSizeModel() {
    }

    public PlateSizeModel(String objectId, PlateModel idPlate, SizeModel size, int price, int timeOfPreparation) {
        this.objectId = objectId;
        this.plate = idPlate;
        this.size = size;
        this.price = price;
        this.timeOfPreparation = timeOfPreparation;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public PlateModel getPlate() {
        return plate;
    }

    public void setPlate(PlateModel plate) {
        this.plate = plate;
    }

    public SizeModel getSize() {
        return size;
    }

    public void setSize(SizeModel size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTimeOfPreparation() {
        return timeOfPreparation;
    }

    public void setTimeOfPreparation(int timeOfPreparation) {
        this.timeOfPreparation = timeOfPreparation;
    }
}
