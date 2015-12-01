package rsantillanc.sanjoylao.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.storage.sp.SJLPreferences;

public class BaseActivity extends AppCompatActivity {
    protected Context _context;
    public int counter;
    private SJLPreferences preferences;
    public Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _context = getApplicationContext();
    }

    protected void showToast(CharSequence sequence) {
        Toast.makeText(getApplicationContext(), sequence, Toast.LENGTH_SHORT).show();
    }

    public void showSnackbar(CharSequence message, View view, int value) {
        Snackbar.make(view, message, value)
                .setAction("Action", null).show();
    }

    public void showSnackbar(CharSequence message, View view, CharSequence actionName) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .setAction(actionName, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.animate();
                    }
                })
                .show();
    }

    public void showMessage(CharSequence sc) {
        showToast(sc);
    }

    public int getCounter() {
        preferences = new SJLPreferences(getApplicationContext());
        return preferences.getCounter();
    }
}
