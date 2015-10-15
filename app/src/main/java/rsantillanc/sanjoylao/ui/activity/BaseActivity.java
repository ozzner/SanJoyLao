package rsantillanc.sanjoylao.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {
    protected Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _context = getApplicationContext();
    }

    protected void showToast(CharSequence sequence){
        Toast.makeText(_context,sequence,Toast.LENGTH_LONG).show();
    }
}
