package rsantillanc.sanjoylao.model;

/**
 * Created by RenzoD on 07/11/2015.
 */
public class APIRequestOrderModel {

    private String objectId;
    private ParsePointerModel idLocationDelivery;
    private ParsePointerModel idOrderType;
    private ParsePointerModel idStatus;
    private ParsePointerModel idPayment;
    private ParsePointerModel idUser;
    private double price;

    public APIRequestOrderModel() {
    }

    public APIRequestOrderModel(String objectId, ParsePointerModel idLocationDelivery, ParsePointerModel idOrderType, ParsePointerModel idStatus, ParsePointerModel idPayment, ParsePointerModel idUser, double price) {
        this.objectId = objectId;
        this.idLocationDelivery = idLocationDelivery;
        this.idOrderType = idOrderType;
        this.idStatus = idStatus;
        this.idPayment = idPayment;
        this.idUser = idUser;
        this.price = price;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public ParsePointerModel getIdLocationDelivery() {
        return idLocationDelivery;
    }

    public void setIdLocationDelivery(ParsePointerModel idLocationDelivery) {
        this.idLocationDelivery = idLocationDelivery;
    }

    public ParsePointerModel getIdOrderType() {
        return idOrderType;
    }

    public void setIdOrderType(ParsePointerModel idOrderType) {
        this.idOrderType = idOrderType;
    }

    public ParsePointerModel getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(ParsePointerModel idStatus) {
        this.idStatus = idStatus;
    }

    public ParsePointerModel getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(ParsePointerModel idPayment) {
        this.idPayment = idPayment;
    }

    public ParsePointerModel getIdUser() {
        return idUser;
    }

    public void setIdUser(ParsePointerModel idUser) {
        this.idUser = idUser;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
