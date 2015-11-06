package rsantillanc.sanjoylao.ui.mvp.Plate;

import java.util.List;

import rsantillanc.sanjoylao.model.PlateModel;

/**
 * Created by rsantillanc on 04/11/2015.
 */
public interface OnPlateListener {
    void onListFilterSuccess(List<PlateModel> platesFilter);
    void onListFilterError(CharSequence error);

}
