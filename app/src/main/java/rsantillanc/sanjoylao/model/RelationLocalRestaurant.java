package rsantillanc.sanjoylao.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RenzoD on 05/12/2015.
 */
public class RelationLocalRestaurant implements Serializable{
    public static final long serialVersionUID = 0L;

    private List<LocalRestaurantModel> locals;





    public List<LocalRestaurantModel> getLocals() {
        return locals;
    }

    public void setLocals(List<LocalRestaurantModel> locals) {
        this.locals = locals;
    }
}
