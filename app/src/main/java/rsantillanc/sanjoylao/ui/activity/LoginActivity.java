package rsantillanc.sanjoylao.ui.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import rsantillanc.sanjoylao.R;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {
    private Toolbar toBa;
    private ImageView ivFacebook,ivGoogle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ivFacebook = (ImageView)findViewById(R.id.iv_facebook);
        ivGoogle = (ImageView)findViewById(R.id.iv_google);

        ivFacebook.setOnClickListener(this);
        ivGoogle.setOnClickListener(this);
        setUpActionBar();

    }


    private void setUpActionBar() {
        toBa = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toBa);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        Intent main =  new Intent(getApplicationContext(),MainActivity.class);
        startActivity(main);
        finish();

    }
}
