package rsantillanc.sanjoylao.ui.mvp.Plate;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import rsantillanc.sanjoylao.R;
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
    PlateActivity view;
    SJLPreferences preferences;

    public PlatePresenterImpl(Activity activity, PlateActivity mView) {
        this.iteractor = new PlateIteractorImpl(activity);
        this.view = mView;
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
        if (platesFilter.size() == 0) {
            view.enabledImageForEmpty(true);
            view.showMessage(mActivity.getString(R.string.error_empty_data));
        } else
            view.onPlatesLoadSuccess(platesFilter);
    }

    @Override
    public void onListFilterError(CharSequence error) {
        view.onError(error);
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

    public void loadCategoryImage(String url, ImageView previewImage) {
        Picasso.with(mActivity)
                .load(url)
                .placeholder(R.drawable.ic_no_image)
                .error(R.drawable.ic_no_image)
                .into(previewImage);
    }
}
