package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by RenzoD on 06/11/2015.
 */
public class OrderModel implements Serializable {
    private static final long serialVersionUID = 0L;


    private String objectId;
    @SerializedName("idLocationDelivery")
    private LocationDeliveryModel locationDelivery;
    @SerializedName("idOrderType")
    private OrderTypeModel orderType;
    @SerializedName("idPayment")
    private PaymentModel payment;
    @SerializedName("idStatus")
    private StatusModel status;
    @SerializedName("idUser")
    private UserModel user;
    private double price;
    private String createdAt;
    private String updatedAt;


    public OrderModel() {
    }

    public OrderModel(String objectId, LocationDeliveryModel locationDelivery, OrderTypeModel orderType, PaymentModel pyment, StatusModel status, UserModel user, double price, String createdAt, String updatedAt) {
        this.objectId = objectId;
        this.locationDelivery = locationDelivery;
        this.orderType = orderType;
        this.payment = pyment;
        this.status = status;
        this.user = user;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public LocationDeliveryModel getLocationDelivery() {
        return locationDelivery;
    }

    public void setLocationDelivery(LocationDeliveryModel locationDelivery) {
        this.locationDelivery = locationDelivery;
    }

    public OrderTypeModel getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderTypeModel orderType) {
        this.orderType = orderType;
    }

    public PaymentModel getPyment() {
        return payment;
    }

    public void setPyment(PaymentModel pyment) {
        this.payment = pyment;
    }

    public StatusModel getStatus() {
        return status;
    }

    public void setStatus(StatusModel status) {
        this.status = status;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
