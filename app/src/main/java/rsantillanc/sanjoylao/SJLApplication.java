package rsantillanc.sanjoylao;

import android.app.Application;

import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by RenzoD on 02/06/2015.
 */
public class SJLApplication extends Application{

    private UserModel localCurrentUser;


    public UserModel getLocalCurrentUser() {
        return localCurrentUser;
    }

    public void setLocalCurrentUser(UserModel localCurrentUser) {
        this.localCurrentUser = localCurrentUser;
    }
}
