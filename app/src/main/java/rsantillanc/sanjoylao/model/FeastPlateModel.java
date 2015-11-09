package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by RenzoD on 09/11/2015.
 */
public class FeastPlateModel implements Serializable{
    private static final long serialVersionUID = 0L;


    private String objectId;
    @SerializedName("idFeast")
    private FeastModel feast;
    @SerializedName("idPlate")
    private PlateModel plate;
    private int timeOfPreparation;
    private String createdAt;
    private String updatedAt;


    public FeastPlateModel(String objectId, FeastModel feast, PlateModel plate, int timeOfPreparation, String createdAt, String updatedAt) {
        this.objectId = objectId;
        this.feast = feast;
        this.plate = plate;
        this.timeOfPreparation = timeOfPreparation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public FeastPlateModel() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public FeastModel getFeast() {
        return feast;
    }

    public void setFeast(FeastModel feast) {
        this.feast = feast;
    }

    public PlateModel getPlate() {
        return plate;
    }

    public void setPlate(PlateModel plate) {
        this.plate = plate;
    }

    public int getTimeOfPreparation() {
        return timeOfPreparation;
    }

    public void setTimeOfPreparation(int timeOfPreparation) {
        this.timeOfPreparation = timeOfPreparation;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
