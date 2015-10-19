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
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.util.Arrays;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.ui.mvp.Login.ILoginView;
import rsantillanc.sanjoylao.ui.mvp.Login.LoginPresenterImpl;
import rsantillanc.sanjoylao.util.Const;

public class LoginActivity extends BaseActivity implements ILoginView,
        View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        FacebookCallback<LoginResult> {

    //Generic constants
    private static final String TAG = LoginActivity.class.getSimpleName() + Const.BLANK_SPACE;

    //Views
    private Toolbar mToolbar;
    private ProgressDialog mProgersDialog;
    private SignInButton mSignInButtonG;
    private LoginButton mLoginButtonF;


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
    private static final String USER_FRIEND = "user_friends";
    private static final String PUBLIC_PROFILE = "public_profile";
    private static final String EMAIL = "email";
    private boolean isFacebookLoginClick = false;


    //----------------[ Activity life cycle ]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

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

        mLoginButtonF = (LoginButton) findViewById(R.id.login_button);
        mSignInButtonG = (SignInButton) findViewById(R.id.sign_in_button);

    }



    //----------------[ Setups components ]

    private void setUpComponents() {
        mPresenter = new LoginPresenterImpl(this);

        setUpFacebook();
        setUpGooglePlus();
        setUpActionBar();
        setUpProgressDialog();
    }

    private void setUpFacebook() {
        mCallbackManager = CallbackManager.Factory.create();

        mLoginButtonF.setReadPermissions(Arrays.asList(PUBLIC_PROFILE, USER_FRIEND, EMAIL));
        mLoginButtonF.setOnClickListener(this);
        mLoginButtonF.registerCallback(mCallbackManager, this);
    }


    private void setUpActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void setUpGooglePlus() {
        mSignInButtonG.setOnClickListener(this);
        mSignInButtonG.setSize(SignInButton.SIZE_ICON_ONLY);

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
        mProgersDialog.setMessage("cargando...");
    }



    //----------------[ Menu handler ]

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            onSignOutClicked();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //----------------[ Others methods ]


    @Override
    public void onClick(View v) {

        if (v == mLoginButtonF) {
            isFacebookLoginClick = true;
        } else {
            shouldResolve = true;
            mGoogleApi.connect();
            isFacebookLoginClick = false;
        }
    }

    private void onSignOutClicked() {
        // Clear the default account so that GoogleApiClient will not automatically
        // connect in the future.
        if (mGoogleApi.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApi);
            mGoogleApi.disconnect();
        }

    }

    private void goToMainActivity(Object obj) {
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        main.putExtras(((Bundle) obj));
        startActivity(main);
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
        mProgersDialog.show();
    }

    @Override
    public void hideLoader() {
        if (mProgersDialog.isShowing())
            mProgersDialog.cancel();
    }

    @Override
    public void onError(CharSequence s) {
        showToast(s);
    }

    @Override
    public void goToDashboard(Object obj) {
        goToMainActivity(obj);
    }


    //----------------[Google plus integration]

    @Override
    public void onConnected(Bundle bundle) {
        shouldResolve = false;

        Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApi);
        if (person != null) {
            mPresenter.onSuccessGoogle(person, mGoogleApi);
        } else {
            mPresenter.onGoogleFailed(person);
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
            mPresenter.onSuccessFacebook(loginResult);
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
