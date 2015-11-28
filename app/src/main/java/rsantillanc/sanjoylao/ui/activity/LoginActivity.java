package rsantillanc.sanjoylao.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.util.Arrays;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.ui.mvp.Login.ILoginView;
import rsantillanc.sanjoylao.ui.mvp.Login.LoginPresenterImpl;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.MenuColorizer;

public class LoginActivity extends BaseActivity implements ILoginView,
        View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        FacebookCallback<LoginResult> {

    //Generic constants
    private static final String TAG = LoginActivity.class.getSimpleName() + Const.BLANK_SPACE;

    //Runtime

    //Views
    private Toolbar mToolbar;
    private ProgressDialog mProgersDialog;
    private Button mButtonGoogle;
    private Button mButtonFacebook;

    //MVP
    private LoginPresenterImpl mPresenter;

    //[Google Plus]
    private GoogleApiClient mGoogleApi = null;
    private static final int RC_SIGN_IN = 0;

    /* Is there a ConnectionResult resolution in progress? */
    private boolean isResolving = false;
    private boolean shouldResolve = false;

    //[Facebook SDK]
    private CallbackManager mCallbackManager;
    private LoginManager mLoginManager;
    private static final String USER_FRIEND = "user_friends";
    private static final String PUBLIC_PROFILE = "public_profile";
    private static final String EMAIL = "email";
    private boolean isFacebookLoginClick = false;


    //----------------[ Activity life cycle ]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getHashKey
        Android.genHashKey(this);

        //Check if can go to main activity
        verifyUserActive();

        //Facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());

        //Set content
        setContentView(R.layout.activity_login);

        //Starting components
        initUIComponents();
        setUpComponents();

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApi != null)
            mGoogleApi.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApi != null)
        if (mGoogleApi.isConnected())
            mGoogleApi.disconnect();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(Const.DEBUG_GOOGLE_PLUS, "onActivityResult: " + requestCode + ":" + resultCode + ":" + data);

        if (isFacebookLoginClick)
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        else {
            //Button login with google plus
            if (requestCode == RC_SIGN_IN) {

                if (resultCode != RESULT_OK) {
                    shouldResolve = false;
                    Log.d(Const.DEBUG_GOOGLE_PLUS, "Activity result error.");
                } else {
                    Log.d(Const.DEBUG_GOOGLE_PLUS, "Activity result correct!");
                }

                isResolving = false;
                mGoogleApi.connect();
            }
        }
    }


    //----------------[ Init views ]

    private void initUIComponents() {
        mButtonFacebook = (Button) findViewById(R.id.bt_login_facebook);
        mButtonGoogle = (Button) findViewById(R.id.bt_login_google);
    }


    //----------------[ Setups components ]

    private void setUpComponents() {
        setUpFacebook();
        setUpGooglePlus();
        setUpActionBar();
        setUpProgressDialog();
    }

    private void setUpFacebook() {
        mLoginManager = LoginManager.getInstance();
        mButtonFacebook.setOnClickListener(this);
    }

    private void loginUsingFacebook() {
        mCallbackManager = CallbackManager.Factory.create();
        mLoginManager.logInWithReadPermissions(this, Arrays.asList(PUBLIC_PROFILE, USER_FRIEND, EMAIL));
        mLoginManager.registerCallback(mCallbackManager, this);
    }


    private void setUpActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void setUpGooglePlus() {
        mButtonGoogle.setOnClickListener(this);

        //Api
        mGoogleApi = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }

    private void setUpProgressDialog() {
        mProgersDialog = new ProgressDialog(this);
        mProgersDialog.setMessage(getString(R.string.progress_message_connecting));

    }


    //----------------[ Menu handler ]

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        int color = getResources().getColor(R.color.white);
        MenuColorizer.colorMenu(this,menu,color);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login_info) {
            showToast("San Joy Lao | V."+Android.getAppVersion(this));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //----------------[ Others methods ]

    @Override
    public void onClick(View v) {

        if (v == mButtonFacebook) {
            loginUsingFacebook();
            isFacebookLoginClick = true;
        } else {
            shouldResolve = true;
            mGoogleApi.connect();
            isFacebookLoginClick = false;
        }

    }

    public void verifyUserActive(){
        mPresenter = new LoginPresenterImpl(this,this);
        mPresenter.getActiveUser();
    }

    private void goToMainActivity(Object obj) {

        //Build data to send
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.EXTRA_USER,((UserModel) obj));

        //Go to main
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        main.putExtras(bundle);
        startActivity(main);

        //save status active
        mPresenter.enabledUser(((UserModel) obj).getEmail());

        //End login
        finish();

    }

    private void showErrorDialog(ConnectionResult Result) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, RC_SIGN_IN,
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                shouldResolve = false;
                                showToast("onCancel dialog error");
                            }
                        }).show();
            } else {
                Log.d(TAG, "Google Play Services Error:" + Result);
                String errorString = apiAvailability.getErrorString(resultCode);
                Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();
                shouldResolve = false;
            }
        }
    }


    //----------------[ ILoginView ]------------------

    @Override
    public void showLoader() {
        if (!mProgersDialog.isShowing())
            mProgersDialog.show();
    }

    @Override
    public void hideLoader() {
            mProgersDialog.hide();
            mProgersDialog.cancel();
    }

    @Override
    public void updateLoader(CharSequence sequence) {
        if (mProgersDialog.isShowing()) {
            mProgersDialog.setMessage(sequence);
        }
    }

    @Override
    public void onError(CharSequence s) {
        hideLoader();
        showToast(s);
    }

    @Override
    public void goToDashboard(Object obj) {
        goToMainActivity(obj);
    }

    @Override
    public void closeGoogleConnection() {
        if (mGoogleApi != null){
            // Clear the default account so that GoogleApiClient will not automatically
            // connect in the future.
            if (mGoogleApi.isConnected()) {
                Plus.AccountApi.clearDefaultAccount(mGoogleApi);
                mGoogleApi.disconnect();
                mGoogleApi.unregisterConnectionCallbacks(this);
                mGoogleApi.unregisterConnectionFailedListener(this);
                mGoogleApi = null;
            }

        }

    }

    @Override
    public void closeFacebookConection() {
        if (mLoginManager != null){
            mLoginManager.logOut();
            mButtonFacebook = null;
            mLoginManager = null;
        }


    }


    //----------------[Google plus integration]

    @Override
    public void onConnected(Bundle bundle) {
        shouldResolve = false;

        Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApi);
        if (person != null) {
            mPresenter.startRequestWithGoogleData(person, mGoogleApi);
        } else {
            mPresenter.onError(getString(R.string.toast_error_null));
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "onConnectionSuspended: " + i);
        mGoogleApi.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        //If need resolve and is not resolving then.
        if (shouldResolve && !isResolving) {

            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    isResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    isResolving = false;
                    mGoogleApi.connect();
                    Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "SendIntentException ", e);
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                showErrorDialog(connectionResult);
            }

        } else {
            //Show again to init login
        }

    }


    //----------------[Facebook login integration]

    @Override
    public void onSuccess(LoginResult loginResult) {
        mPresenter.startRequestWithFacebookData(loginResult);
    }

    @Override
    public void onCancel() {
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "onCancel: ");
    }

    @Override
    public void onError(FacebookException error) {
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "FacebookException: " + error.getMessage());
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "FacebookException: " + error.getCause());

    }
}
