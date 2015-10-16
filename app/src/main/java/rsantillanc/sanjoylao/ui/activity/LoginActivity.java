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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.ui.mvp.Login.ILoginView;
import rsantillanc.sanjoylao.ui.mvp.Login.LoginPresenterImpl;
import rsantillanc.sanjoylao.util.Const;

public class LoginActivity extends BaseActivity implements ILoginView,
        View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    //Generic constants
    private static final String TAG = LoginActivity.class.getSimpleName() + Const.BLANK_SPACE;

    //Views
    private Toolbar mToolbar;
    private ImageView ivFacebook, ivGoogle;
    private ProgressDialog mProgersDialog;
    private SignInButton mSignInButtonG;

    //MVP
    private LoginPresenterImpl mPresenter;

    //[Google Plus]
    private GoogleApiClient mGoogleApi = null;

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /*prevents us from starting further intents.*/
    private boolean mIntentInProgress;

    /* Is there a ConnectionResult resolution in progress? */
    private boolean isResolving;


    //----------------[ Activity life cycle ]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        //Button login with google plus
        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further.
            if (resultCode != RESULT_OK) {
               showToast("Error activity result");
            }else
                showToast("Activity result correct!");

//            mIsResolving = false;
            mGoogleApi.connect();
    }

    }

    //----------------[ Init views ]

    private void initUIComponents() {

        ivFacebook = (ImageView) findViewById(R.id.iv_facebook);
        mSignInButtonG = (SignInButton) findViewById(R.id.sign_in_button);

    }


    //----------------[ Setups components ]

    private void setUpComponents() {
        mPresenter = new LoginPresenterImpl(this);

        ivFacebook.setOnClickListener(this);
        mSignInButtonG.setOnClickListener(this);

        setUpGooglePlus();
        setUpActionBar();
        setUpProgressDialog();
    }


    private void setUpActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void setUpGooglePlus() {
        mGoogleApi = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(new Scope(Scopes.PROFILE))
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
        if (id == R.id.action_settings) {
            onSignOutClicked();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //----------------[ Others methods ]

    @Override
    public void onClick(View v) {

        if (v == ivFacebook) {
            mPresenter.connectWithFacebook();
        } else {
            mPresenter.connectWithGoogle();
        }
    }

    private void onSignOutClicked() {
        // Clear the default account so that GoogleApiClient will not automatically
        // connect in the future.
        showToast("Bye.");
        if (mGoogleApi.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApi);
            mGoogleApi.disconnect();
        }

//        showSignedOutUI();
    }

    private void goToMainActivity() {
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
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
//                                mShouldResolve = false;
//                                showSignedOutUI();
                            }
                        }).show();
            } else {
                Log.d(TAG, "Google Play Services Error:" + Result);
                String errorString = apiAvailability.getErrorString(resultCode);
                Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();

//                mShouldResolve = false;
//                showSignedOutUI();
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
    public void goToDashboard() {
        goToMainActivity();
    }


    //----------------[Google plus integration]

    @Override
    public void onConnected(Bundle bundle) {

        if (bundle != null)
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Bundle " +bundle.toString());

        try {
            Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApi);

            if (person!= null) {

                if (person.hasImage()){
                 Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person Image getImageUrl" +person.getImage().getUrl());
                }

                Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person getDisplayName " +person.getDisplayName());
                Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person getUrl " +person.getUrl());
                Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person Gender " +person.getGender());
                Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person Name " +person.getName());
                Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person Birthday " +person.getBirthday());
                Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person getAboutMe " +person.getAboutMe());
                Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person hasTagline " +person.hasTagline());
                Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "AccountName email " +  Plus.AccountApi.getAccountName(mGoogleApi));


            } else {
                Toast.makeText(getApplicationContext(),
                        "Couldnt Get the Person Info", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "onConnectionSuspended: " + i);
        mGoogleApi.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "onConnectionFailed ConnectionResult: " + connectionResult.isSuccess());
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "onConnectionFailed ConnectionResult: " + connectionResult.isSuccess());

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

    }


}
