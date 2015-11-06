package rsantillanc.sanjoylao.model;

import java.io.Serializable;

/**
 * Created by RenzoD on 06/11/2015.
 */
public class LocationDeliveryModel implements Serializable {
    private static final long serialVersionUID = 0L;

    private String objectId;
    private ParseGeoPointModel location;

    public LocationDeliveryModel(String objectId, ParseGeoPointModel location) {
        this.objectId = objectId;
        this.location = location;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public ParseGeoPointModel getLocation() {
        return location;
    }

    public void setLocation(ParseGeoPointModel location) {
        this.location = location;
    }
}
