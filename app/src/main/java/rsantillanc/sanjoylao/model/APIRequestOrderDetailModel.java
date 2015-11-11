package rsantillanc.sanjoylao.model;

/**
 * Created by RenzoD on 07/11/2015.
 */
public class APIRequestOrderDetailModel {

    private String objectId;
    private ParsePointerModel idFeastPlate;
    private ParsePointerModel idOrder;
    private ParsePointerModel idPlateSize;
    private int counter;

    public APIRequestOrderDetailModel() {
    }

    public APIRequestOrderDetailModel(String objectId, ParsePointerModel idLocationDelivery, ParsePointerModel idOrderType, ParsePointerModel idStatus) {
        this.objectId = objectId;
        this.idFeastPlate = idLocationDelivery;
        this.idOrder = idOrderType;
        this.idPlateSize = idStatus;

    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public ParsePointerModel getIdFeastPlate() {
        return idFeastPlate;
    }

    public void setIdFeastPlate(ParsePointerModel idFeastPlate) {
        this.idFeastPlate = idFeastPlate;
    }

    public ParsePointerModel getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(ParsePointerModel idOrder) {
        this.idOrder = idOrder;
    }

    public ParsePointerModel getIdPlateSize() {
        return idPlateSize;
    }

    public void setIdPlateSize(ParsePointerModel idPlateSize) {
        this.idPlateSize = idPlateSize;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
