package rsantillanc.sanjoylao.ui.mvp.Map;

import java.util.List;

import rsantillanc.sanjoylao.model.LocalRestaurantModel;

/**
 * Created by Computer on 15/12/2015.
 */
public interface IMapView  {
    void printLocals(List<LocalRestaurantModel> locals);
}
