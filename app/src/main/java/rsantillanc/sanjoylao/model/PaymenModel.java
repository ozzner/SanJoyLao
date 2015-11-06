package rsantillanc.sanjoylao.model;

import java.io.Serializable;

/**
 * Created by RenzoD on 06/11/2015.
 */
public class PaymenModel implements Serializable {

    private static final long serialVersionUID = 0L;

    private String objectId;
    private String banco;
    private String transactionCode;
    private String type;
    private String createdAt;
    private String updatedAt;

    public PaymenModel(String objectId, String banco, String transactionCode, String type, String createdAt, String updatedAt) {
        this.objectId = objectId;
        this.banco = banco;
        this.transactionCode = transactionCode;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PaymenModel() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
