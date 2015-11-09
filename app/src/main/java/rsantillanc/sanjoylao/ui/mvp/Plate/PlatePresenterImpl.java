package rsantillanc.sanjoylao.ui.mvp.Plate;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.RelationPlateSizeModel;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.ui.activity.PlateActivity;

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

    public PlatePresenterImpl() {
        mActivity = null;
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
    public void onListFilterSuccess(List<RelationPlateSizeModel> platesFilter) {
        mView.onPlatesLoadSuccess(platesFilter);
    }

    @Override
    public void onListFilterError(CharSequence error) {
        mView.onError(error);
    }

    @Override
    public void onPlateAddSucess(Context c, int size) {
        IPlateView view = new PlateActivity();
        view.onPlateAddOrderCorrect(c,size);
    }

    public void addPlateToOrder(PlateSizeModel plateSize, UserModel user) {
        iteractor.addPlate(mActivity,plateSize,user);
    }
}
