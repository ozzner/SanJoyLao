package rsantillanc.sanjoylao.model;

/**
 * Created by RenzoD on 11/12/2015.
 */
public class PushOrderModel extends BaseModel {

    private String orderObjectId;
    private int statusCode;
    private int estimatedTime;
    private String push_hash;


    public PushOrderModel(String orderObjectId, int statusCode, int timeEstimated) {
        this.orderObjectId = orderObjectId;
        this.statusCode = statusCode;
        this.estimatedTime = timeEstimated;
    }

    public PushOrderModel() {
    }

    public String getOrderObjectId() {
        return orderObjectId;
    }

    public void setOrderObjectId(String orderObjectId) {
        this.orderObjectId = orderObjectId;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getPush_hash() {
        return push_hash;
    }

    public void setPush_hash(String push_hash) {
        this.push_hash = push_hash;
    }
}
