package rsantillanc.sanjoylao;

import android.app.Application;

import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by RenzoD on 02/06/2015.
 */
public class SJLApplication extends Application{

    private UserModel userLogued;

    public UserModel getUserLogued() {
        return userLogued;
    }

    public void setUserLogued(UserModel userLogued) {
        this.userLogued = userLogued;
    }
}
