package rsantillanc.sanjoylao.ui.mvp.Plate;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.RelationPlateSizeModel;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.storage.sp.SJLPreferences;
import rsantillanc.sanjoylao.ui.activity.PlateActivity;

/**
 * Created by RenzoD on 29/10/2015.
 */
public class PlatePresenterImpl implements IPlatePresenter, OnPlateListener {

    private final Activity mActivity;
    PlateIteractorImpl iteractor;
    IPlateView mView;
    SJLPreferences preferences;

    public PlatePresenterImpl(Activity activity, IPlateView mView) {
        this.iteractor = new PlateIteractorImpl(activity);
        this.mView = mView;
        this.mActivity = activity;
        this.preferences = new SJLPreferences(activity);
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
    public void onPlateAddSuccess(Context c) {
        preferences = new SJLPreferences(c);
        preferences.saveCounter(preferences.getCounter() + 1);
        PlateActivity view = new PlateActivity();
        view.onPlateAddOrderCorrect(c, preferences.getCounter());
    }

    public void addPlateToOrder(PlateSizeModel plateSize, UserModel user) {
        iteractor.addPlate(mActivity, plateSize, user);
    }

    public void onPlateCounterSuccess(Context c, CharSequence ok, long counter) {
        if (preferences == null)
            preferences = new SJLPreferences(c);

        preferences.saveCounter(preferences.getCounter() + 1);
        PlateActivity v = new PlateActivity();
        v.onPlateCounterUpdated(c, ok, preferences.getCounter());
    }
}
