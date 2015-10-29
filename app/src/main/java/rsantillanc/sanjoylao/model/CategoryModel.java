package rsantillanc.sanjoylao.model;

import java.io.Serializable;

/**
 * Created by rsantillanc on 29/10/2015.
 */
public class CategoryModel implements Serializable {
    private static final long serialVersionUID = 0L;

    private String objectId;
    private String name;
    private String createdAt;
    private String updatedAt;


    public CategoryModel() {
    }

    public CategoryModel(String updatedAt, String createdAt, String name, String objectId) {
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.name = name;
        this.objectId = objectId;
    }


    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
