package rsantillanc.sanjoylao.ui.mvp.Profile;

import android.widget.EditText;

import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface IProfilePresenter {
    void validateFields(EditText[] fields);
    void save(EditText[] fields, UserModel userSerializable);
}
