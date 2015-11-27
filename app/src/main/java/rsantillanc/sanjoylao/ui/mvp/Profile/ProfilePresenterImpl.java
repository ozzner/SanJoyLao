package rsantillanc.sanjoylao.ui.mvp.Profile;

import android.text.TextUtils;
import android.widget.EditText;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.interfaces.OnSaveListener;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.ui.activity.ProfileActivity;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class ProfilePresenterImpl implements IProfilePresenter,OnSaveListener {


    private ProfileActivity view;
    private ProfileIteractorImpl iteractor;

    public ProfilePresenterImpl(IProfileView view) {
        this.view = (ProfileActivity) view;
        iteractor = new ProfileIteractorImpl((ProfileActivity) view);
    }

    @Override
    public void validateFields(EditText[] fields) {
        boolean error = false;
        for (int i = 0; i < fields.length; i++)
            if (TextUtils.isEmpty(fields[i].getText().toString().trim()))
                error = true;

        if (error) view.validateFieldsError();
        else view.validateFieldsOk();
    }


    /**
     *
     * @param fields 0 = Identity, 1 = phoneNumber and 2 = birthDay
     * @param userSerializable
     */
    @Override
    public void save(EditText[] fields, UserModel userSerializable) {
        userSerializable.setIdentificationDocument(Long.parseLong(fields[0].getText().toString().trim()));
        userSerializable.setPhoneNumber(Long.parseLong(fields[1].getText().toString().trim()));
        userSerializable.setBirthday((fields[2].getText().toString().trim()));
        iteractor.saveIn(userSerializable, this);
    }

    @Override
    public void onSaveSuccess() {
        view.showMessage(view.getString(R.string.message_save_correct));
    }

    @Override
    public void onSaveError(CharSequence error) {
        view.showMessage(error);
    }
}
