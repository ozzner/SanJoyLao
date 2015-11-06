package rsantillanc.sanjoylao.model;

import java.io.Serializable;

/**
 * Created by RenzoD on 06/11/2015.
 */
public class StatusModel implements Serializable {
    private static final long serialVersionUID = 0L;

    //Attributes
    private String objectId;
    private int code;
    private String description;
    private String name;

    public StatusModel(String objectId, int code, String description, String name) {
        this.objectId = objectId;
        this.code = code;
        this.description = description;
        this.name = name;
    }

    public StatusModel() {
    }


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
