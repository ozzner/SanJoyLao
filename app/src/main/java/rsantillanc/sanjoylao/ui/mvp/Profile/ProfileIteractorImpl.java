package rsantillanc.sanjoylao.ui.mvp.Profile;

import rsantillanc.sanjoylao.interfaces.OnSaveListener;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.storage.dao.UserDao;
import rsantillanc.sanjoylao.ui.activity.ProfileActivity;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class ProfileIteractorImpl implements IProfileIteractor {
    private ProfileActivity currentActivity;

    public ProfileIteractorImpl(ProfileActivity activity) {
        this.currentActivity = activity;
    }


    @Override
    public void saveIn(UserModel userSerializable, OnSaveListener saveListener) {
        boolean ok = new UserDao(currentActivity).update(userSerializable) > 0;
        if (ok)saveListener.onSaveSuccess();
        else saveListener.onSaveError("Error!");
    }
}
