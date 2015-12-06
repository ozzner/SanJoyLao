package rsantillanc.sanjoylao.model;

import java.io.Serializable;

/**
 * Created by RenzoD on 05/12/2015.
 */
public class RelationOrder implements Serializable {

    public static final long serialVersionUID = 0L;

    RelationLocalRestaurant localRestaurant;
    OrderModel currentOrder;


    public OrderModel getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(OrderModel currentOrder) {
        this.currentOrder = currentOrder;
    }

    public RelationLocalRestaurant getLocalRestaurant() {
        return localRestaurant;
    }

    public void setLocalRestaurant(RelationLocalRestaurant localRestaurant) {
        this.localRestaurant = localRestaurant;
    }
}
