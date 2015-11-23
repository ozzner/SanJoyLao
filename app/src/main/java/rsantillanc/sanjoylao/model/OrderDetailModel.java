package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by RenzoD on 08/11/2015.
 */
public class OrderDetailModel implements Serializable {
    private static final long serialVersionUID = 0L;

    private String objectId;
    @SerializedName("idFeastPlate")
    private FeastPlateModel feastPlate;
    @SerializedName("idOrder")
    private OrderModel order;
    @SerializedName("idPlateSize")
    private PlateSizeModel plateSize;
    private int counter;
    private String createdAt;
    private String updatedAt;

    public OrderDetailModel() {
    }

    public OrderDetailModel(String objectId, FeastPlateModel feastPlate, OrderModel order, PlateSizeModel plateSize, String createdAt, String updatedAt, int counter) {
        this.objectId = objectId;
        this.feastPlate = feastPlate;
        this.order = order;
        this.plateSize = plateSize;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.counter = counter;
    }


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public FeastPlateModel getFeastPlate() {
        return feastPlate;
    }

    public void setFeastPlate(FeastPlateModel feastPlate) {
        this.feastPlate = feastPlate;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public PlateSizeModel getPlateSize() {
        return plateSize;
    }

    public void setPlateSize(PlateSizeModel plateSize) {
        this.plateSize = plateSize;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
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
