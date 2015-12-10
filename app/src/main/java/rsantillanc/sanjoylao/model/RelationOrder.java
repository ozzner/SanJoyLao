package rsantillanc.sanjoylao.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RenzoD on 05/12/2015.
 */
public class RelationOrder implements Serializable {

    public static final long serialVersionUID = 0L;

    RelationLocalRestaurant localRestaurant;
    OrderModel order;
    List<OrderDetailModel> orderDetails;


    public List<OrderDetailModel> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailModel> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public RelationLocalRestaurant getLocalRestaurant() {
        return localRestaurant;
    }

    public void setLocalRestaurant(RelationLocalRestaurant localRestaurant) {
        this.localRestaurant = localRestaurant;
    }
}
