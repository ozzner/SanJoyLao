package rsantillanc.sanjoylao.ui.mvp.Map;

import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.model.LocalRestaurantModel;

/**
 * Created by Computer on 15/12/2015.
 */
public interface IMapIteractor {
    List<LocalRestaurantModel> retrieveLocals(Context c);
}
