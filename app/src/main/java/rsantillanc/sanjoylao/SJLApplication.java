package rsantillanc.sanjoylao;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 02/06/2015.
 */
public class SJLApplication extends Application {

    private UserModel userLogued;

    public UserModel getUserLogued() {
        return userLogued;
    }

    public void setUserLogued(UserModel userLogued) {
        this.userLogued = userLogued;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, getString(R.string.parse_application_id), getString(R.string.parse_client_key));
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground(Const.SJL_CHANNEL, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null)
                    Log.e("SJLApplication", "Successfully subscribed to Parse!");
                else
                    Log.e("SJLApplication", "Error subscribed to Parse: " + e.getMessage());
            }
        });
    }
}
