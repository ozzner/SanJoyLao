package rsantillanc.sanjoylao.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rsantillanc.sanjoylao.R;

public class ViewerActivity extends BaseActivity implements View.OnClickListener{

    private ImageView ivClose;
    private TextView tvOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        initUIElements();
        setupUIElements();
    }

    private void initUIElements() {
        ivClose = (ImageView)findViewById(R.id.iv_viewer_close);
        tvOpen = (TextView)findViewById(R.id.tv_viewer_open);
    }


    private void setupUIElements() {
        ivClose.setOnClickListener(this);
        tvOpen.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_viewer, menu);
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
        if (v instanceof ImageView)
            finish();
        else
            showToast("¡Open plate!");
    }
}
