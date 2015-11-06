package rsantillanc.sanjoylao.ui.mvp.Category;

import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.model.CategoryModel;
import rsantillanc.sanjoylao.storage.dao.CategoryDao;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class CategoryIteractorImpl implements ICategoryIteractor {

    @Override
    public List<CategoryModel> listCategories(Context c) {
        return new CategoryDao(c).list();
    }
}
