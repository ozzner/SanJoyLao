package rsantillanc.sanjoylao.ui.mvp.Category;

import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.model.CategoryModel;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface ICategoryIteractor {
     List<CategoryModel> listCategories(Context c);
}
