package rsantillanc.sanjoylao.ui.mvp.Plate;

import android.app.Activity;

import java.util.List;

import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by RenzoD on 29/10/2015.
 */
public class PlatePresenterImpl implements IPlatePresenter,OnPlateListener {

    private final Activity mActivity;
    PlateIteractorImpl iteractor;
    IPlateView mView;

    public PlatePresenterImpl(Activity activity, IPlateView mView) {
        this.iteractor = new PlateIteractorImpl(activity);
        this.mView = mView;
        this.mActivity = activity;
    }


    @Override
    public void onPlateClick(PlateModel plate) {

    }

    @Override
    public void loadPlatesByCategory(String categoryID) {
        iteractor.findPlatesByCategory(categoryID, this);
    }


    //{ON_PLATE_LISTENER}
    @Override
    public void onListFilterSuccess(List<PlateModel> platesFilter) {
        mView.onPlatesLoadSuccess(platesFilter);
    }

    @Override
    public void onListFilterError(CharSequence error) {
        mView.onError(error);
    }

    public void addPlateToOrder(PlateSizeModel plateSize, UserModel user) {
        iteractor.addPlate(mActivity,plateSize,user);
    }
}
