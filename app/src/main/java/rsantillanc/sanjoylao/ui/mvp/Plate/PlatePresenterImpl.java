package rsantillanc.sanjoylao.ui.mvp.Plate;

import android.app.Activity;

import rsantillanc.sanjoylao.model.PlateModel;

/**
 * Created by RenzoD on 29/10/2015.
 */
public class PlatePresenterImpl implements IPlatePresenter {

    private final Activity mActivity;
    PlateIteractorImpl iteractor;
    IPlateView mView;

    public PlatePresenterImpl(Activity activity, IPlateView mView) {
        this.iteractor = new PlateIteractorImpl();
        this.mView = mView;
        this.mActivity = activity;
    }


    @Override
    public void onPlateClick(PlateModel plate) {

    }

    @Override
    public void loadPlatesByCategory(CharSequence categoryID) {
        iteractor.findPlatesByCategory(makeJson(categoryID));
    }

    private String makeJson(CharSequence categoryID) {
        String s = "{\"idCategory\":{\"__type\":\"Pointer\",\"className\":\"Category\",\"objectId\":\"" + categoryID + "\"}}";
        return s;
    }
}
