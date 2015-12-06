package rsantillanc.sanjoylao.ui.mvp.Plate;

import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.model.RelationPlateSize;

/**
 * Created by rsantillanc on 04/11/2015.
 */
public interface OnPlateListener {
    void onListFilterSuccess(List<RelationPlateSize> platesFilter);
    void onListFilterError(CharSequence error);
    void onPlateAddSuccess(Context c);

}
