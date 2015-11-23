package rsantillanc.sanjoylao;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 02/06/2015.
 */
public class SJLApplication extends Application {

    private UserModel currentUser;

    public UserModel getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserModel currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, getString(R.string.parse_application_id), getString(R.string.parse_client_key));
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground(Const.SJL_CHANNEL);
    }
}
