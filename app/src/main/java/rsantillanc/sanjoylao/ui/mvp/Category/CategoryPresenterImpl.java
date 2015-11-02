package rsantillanc.sanjoylao.ui.mvp.Category;

import android.app.Activity;

import java.util.List;

import rsantillanc.sanjoylao.model.CategoryModel;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class CategoryPresenterImpl {

    private final Activity mActivity;
    ICategoryView mView;
    CategoryIteractorImpl mIteractor;

    public CategoryPresenterImpl(ICategoryView view,Activity activity) {
        this.mView  = view;
        this.mActivity  = activity;
        this.mIteractor = new CategoryIteractorImpl();
    }

    public List<CategoryModel> getCategoryList() {
        return mIteractor.listCategories(mActivity);
    }
}
