package rsantillanc.sanjoylao.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RenzoD on 04/12/2015.
 */
public class LocalRestaurantModel extends BaseModel {

    private boolean deliveryAvailable;
    @SerializedName("idRestaurant")
    private RestaurantModel restaurant;
    private ParseGeoPointModel location;
    private String address;
    private boolean reservationAvailable;


    public LocalRestaurantModel() {
    }

    public LocalRestaurantModel(boolean deliveryAvailable, RestaurantModel restaurant, ParseGeoPointModel location, String address, boolean reservationAvailable) {
        this.deliveryAvailable = deliveryAvailable;
        this.restaurant = restaurant;
        this.location = location;
        this.address = address;
        this.reservationAvailable = reservationAvailable;
    }


    public boolean isDeliveryAvailable() {
        return deliveryAvailable;
    }

    public void setDeliveryAvailable(boolean deliveryAvailable) {
        this.deliveryAvailable = deliveryAvailable;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModel restaurant) {
        this.restaurant = restaurant;
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

    public boolean isReservationAvailable() {
        return reservationAvailable;
    }

    public void setReservationAvailable(boolean reservationAvailable) {
        this.reservationAvailable = reservationAvailable;
    }
}



