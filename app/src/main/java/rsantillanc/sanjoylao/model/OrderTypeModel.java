package rsantillanc.sanjoylao.model;

import java.io.Serializable;

/**
 * Created by RenzoD on 05/11/2015.
 */
public class OrderTypeModel implements Serializable {
    private static final long serialVersionUID = 0L;


    private String objectId;
    private String name;

    public OrderTypeModel(String objectId, String name) {
        this.objectId = objectId;
        this.name = name;
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
}
