package rsantillanc.sanjoylao.model;

import java.io.Serializable;

/**
 * Created by RenzoD on 04/12/2015.
 */
public abstract class BaseModel implements Serializable{
    private static final long serialVersionUID = 0L;

    protected String objectId;
    protected String createdAt;
    protected String updatedAt;


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
