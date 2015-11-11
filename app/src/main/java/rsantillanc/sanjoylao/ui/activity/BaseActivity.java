package rsantillanc.sanjoylao.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {
    protected Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _context = getApplicationContext();
    }

    protected void showToast(CharSequence sequence){
        Toast.makeText(getApplicationContext(),sequence,Toast.LENGTH_SHORT).show();
    }

    protected void showSnackbar(CharSequence message, View view){
        Snackbar.make(view,message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }
}
