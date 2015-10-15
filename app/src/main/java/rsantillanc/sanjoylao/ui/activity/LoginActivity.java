package rsantillanc.sanjoylao.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.ui.mvp.Login.ILoginView;
import rsantillanc.sanjoylao.ui.mvp.Login.LoginPresenterImpl;

public class LoginActivity extends BaseActivity implements View.OnClickListener, ILoginView {

    //Views
    private Toolbar mToolbar;
    private ImageView ivFacebook, ivGoogle;
    private ProgressDialog mProgersDialog;

    //MVP
    private LoginPresenterImpl mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUIComponents();
        setUpComponents();


    }

    private void setUpComponents() {
        mPresenter = new LoginPresenterImpl(this);
        ivFacebook.setOnClickListener(this);
        ivGoogle.setOnClickListener(this);

        setUpActionBar();
        setUpProgressDialog();
    }

    private void initUIComponents() {

        ivFacebook = (ImageView) findViewById(R.id.iv_facebook);
        ivGoogle = (ImageView) findViewById(R.id.iv_google);

    }


    private void setUpActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUpProgressDialog(){
        mProgersDialog = new ProgressDialog(this);
        mProgersDialog.setMessage("cargando...");
    };

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v == ivFacebook){
            mPresenter.connectWithFacebook();
        }else {
            mPresenter.connectWithGoogle();
        }


    }

    private void goToMainActivity() {
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(main);
        finish();
    }


    //---------------- Implements callbacks--------------------

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
}
