package rsantillanc.sanjoylao.ui.mvp.Plate;

import rsantillanc.sanjoylao.model.PlateModel;

/**
 * Created by RenzoD on 29/10/2015.
 */
public interface IPlatePresenter {
    void onPlateClick(PlateModel plate);
    void loadPlatesByCategory(CharSequence categoryID);
}
