package rsantillanc.sanjoylao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.SJLApplication;
import rsantillanc.sanjoylao.model.CommentModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.ui.custom.adapter.RecyclerCommentAdapter;
import rsantillanc.sanjoylao.ui.custom.view.DividerItemDecoration;
import rsantillanc.sanjoylao.ui.mvp.PlateDetail.IPlateDetailView;
import rsantillanc.sanjoylao.ui.mvp.PlateDetail.PlateDetailPresenterImpl;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.MenuColorizer;

public class PlateDetailActivity extends BaseActivity implements IPlateDetailView, View.OnClickListener {

    //View
    private Toolbar toolbar;
    private ProgressBar pbLoader;
    private RecyclerView rcvComments;
    private FrameLayout layZoomImage;
    private FrameLayout layIngredients;
    private LinearLayout layBody;
    private LinearLayout layAddNew;

    //Runtime
    private RecyclerCommentAdapter mAdapter;
    private PlateDetailPresenterImpl presenter;
    private SJLApplication app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_detail);

        //Data & context
        init();

        //Views
        initUIElements();

        //Config
        setupUIElements();


    }

    private void setupUIElements() {
        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(((PlateModel) getIntent().getExtras().getSerializable(Const.EXTRA_PLATE_DETAIL)).getName());

        //Listeners
        layIngredients.setOnClickListener(this);
        layZoomImage.setOnClickListener(this);
        layAddNew.setOnClickListener(this);

        presenter.loadComments(getIntent().getExtras().getSerializable(Const.EXTRA_PLATE_DETAIL));
    }

    private void initUIElements() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pbLoader = (ProgressBar) findViewById(R.id.pg_plate_detail_loader);
        rcvComments = (RecyclerView) findViewById(R.id.rcv_plates_detail_comments);
        layIngredients = (FrameLayout) findViewById(R.id.lay_plate_detail_ingredients);
        layZoomImage = (FrameLayout) findViewById(R.id.lay_plate_detail_zoom);
        layBody = (LinearLayout) findViewById(R.id.lay_body);
        layAddNew = (LinearLayout) findViewById(R.id.lay_add_new);
    }

    private void init() {
        app = ((SJLApplication) getApplication());
        presenter = new PlateDetailPresenterImpl(this, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plate_detail, menu);
        MenuColorizer.colorMenuItem(menu.getItem(0), getResources().getColor(R.color.white));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_new_comment)
            presenter.showAlertDialogComment((getIntent().getExtras().getSerializable(Const.EXTRA_PLATE_DETAIL)), app.getCurrentUser());
        else
            finish();

        return true;
    }

    //Click
    @Override
    public void onClick(View view) {
        if (view.equals(layIngredients))
            presenter.showIngredients(((PlateModel) getIntent().getExtras().getSerializable(Const.EXTRA_PLATE_DETAIL)));
        else if (view.equals(layZoomImage))
            presenter.openImage();
        else
            presenter.showAlertDialogComment((getIntent().getExtras().getSerializable(Const.EXTRA_PLATE_DETAIL)), app.getCurrentUser());
    }

    //---{CallBacks IView}
    @Override
    public void onCommentsSuccess(List<CommentModel> listComments) {
        mAdapter = new RecyclerCommentAdapter(listComments, getApplicationContext());
        rcvComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcvComments.setHasFixedSize(false);
        rcvComments.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL_LIST));
        rcvComments.setItemAnimator(new DefaultItemAnimator());
        rcvComments.setAdapter(mAdapter);
    }

    @Override
    public void onError(CharSequence cs) {
        showToast(cs);
    }

    @Override
    public void showLoader() {
        pbLoader.setVisibility(View.VISIBLE);
        rcvComments.setVisibility(View.GONE);
    }

    @Override
    public void hideLoader() {
        pbLoader.setVisibility(View.GONE);
        rcvComments.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAddNew() {
        layBody.setVisibility(View.VISIBLE);
        layAddNew.setVisibility(View.GONE);
    }

    @Override
    public void showAddNew() {
        layBody.setVisibility(View.GONE);
        layAddNew.setVisibility(View.VISIBLE);

    }

    @Override
    public void showMessage(String s) {
        showToast(s);
    }

    @Override
    public void addComment(CommentModel newComment) {
        mAdapter.getComments().add(0, newComment);
        mAdapter.notifyDataSetChanged();
    }


}
