package rsantillanc.sanjoylao.ui.mvp.Profile;

import rsantillanc.sanjoylao.interfaces.OnSaveListener;
import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface IProfileIteractor {
    void saveIn(UserModel userSerializable,OnSaveListener saveListener);
}
