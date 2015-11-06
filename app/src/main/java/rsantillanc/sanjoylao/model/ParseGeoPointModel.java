package rsantillanc.sanjoylao.model;

/**
 * Created by RenzoD on 06/11/2015.
 */
public class ParseGeoPointModel {

    private String __type ;
    private double latitude;
    private double longitude;

    public ParseGeoPointModel(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
