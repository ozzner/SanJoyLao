package rsantillanc.sanjoylao.ui.mvp.Plate;

import java.util.List;

import rsantillanc.sanjoylao.model.PlateModel;

/**
 * Created by RenzoD on 29/10/2015.
 */
public interface IPlateView {
    void onPlatesLoadSuccess(List<PlateModel> plates);
    void goToPlateDetail(PlateModel plate);
    void onError(CharSequence error);
}
