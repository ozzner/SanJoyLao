package rsantillanc.sanjoylao.ui.mvp.Map;

import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.model.LocalRestaurantModel;
import rsantillanc.sanjoylao.storage.dao.LocalRestaurantDao;
import rsantillanc.sanjoylao.ui.activity.MapActivity;

/**
 * Created by Computer on 15/12/2015.
 */
public class MapIteractorImpl implements IMapIteractor {

    MapActivity viewContext;

    public MapIteractorImpl(MapActivity mapView) {
        this.viewContext = mapView;
    }


    @Override
    public List<LocalRestaurantModel> retrieveLocals(Context c) {
        return new LocalRestaurantDao(c).getAll();
    }
}
