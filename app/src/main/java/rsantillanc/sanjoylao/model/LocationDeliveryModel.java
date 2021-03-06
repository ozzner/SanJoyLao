package rsantillanc.sanjoylao.model;

/**
 * Created by RenzoD on 06/11/2015.
 */
public class LocationDeliveryModel extends BaseModel{
    private static final long serialVersionUID = 0L;

    private String address;
    private String reference;
    private ParseGeoPointModel location;

    public LocationDeliveryModel(String objID, ParseGeoPointModel location) {
        this.objectId = objID;
        this.location = location;
    }

    public LocationDeliveryModel() {

    }

    public ParseGeoPointModel getLocation() {
        return location;
    }

    public void setLocation(ParseGeoPointModel location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
