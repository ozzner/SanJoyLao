package rsantillanc.sanjoylao.ui.mvp.Plate;

import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.RelationPlateSize;

/**
 * Created by RenzoD on 29/10/2015.
 */
public interface IPlateView {
    void onPlatesLoadSuccess(List<RelationPlateSize> plates);
    void goToPlateDetail(PlateModel plate);
    void onError(CharSequence error);
    void onPlateAddOrderCorrect(Context c, int size);
    void onPlateCounterUpdated(Context c, CharSequence ok, long counter);
    void enabledImageForEmpty(boolean on);
}
