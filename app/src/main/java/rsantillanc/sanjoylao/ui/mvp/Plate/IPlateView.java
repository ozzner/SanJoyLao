package rsantillanc.sanjoylao.ui.mvp.Plate;

import java.util.List;

import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.RelationPlateSizeModel;

/**
 * Created by RenzoD on 29/10/2015.
 */
public interface IPlateView {
    void onPlatesLoadSuccess(List<RelationPlateSizeModel> plates);
    void goToPlateDetail(PlateModel plate);
    void onError(CharSequence error);
    void onPlateAddOrderCorrect(int size);
}
