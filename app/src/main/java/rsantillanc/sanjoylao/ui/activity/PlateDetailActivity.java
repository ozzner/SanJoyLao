package rsantillanc.sanjoylao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.CommentModel;
import rsantillanc.sanjoylao.ui.mvp.PlateDetail.IPlateDetailView;
import rsantillanc.sanjoylao.ui.mvp.PlateDetail.PlateDetailPresenterImpl;
import rsantillanc.sanjoylao.util.Const;

public class PlateDetailActivity extends BaseActivity implements IPlateDetailView {

    private Toolbar toolbar;
    private ProgressBar pbLoader;
    private RecyclerView rcvComments;


    private PlateDetailPresenterImpl mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_detail);

        //Data & context
        init();

        //views
        initUIElements();

        //Config
        setupUIElements();


    }

    private void setupUIElements() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initUIElements() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pbLoader = (ProgressBar) findViewById(R.id.pg_plate_detail_loader);
        rcvComments = (RecyclerView)findViewById(R.id.rcv_plates_detail_comments);
    }

    private void init() {
        mPresenter = new PlateDetailPresenterImpl(this, this);
        mPresenter.loadComments(getIntent().getExtras().getSerializable(Const.EXTRA_PLATE_DETAIL));
    }

    //---{CallBacks IView}
    @Override
    public void onCommentsSuccess(List<CommentModel> listComments) {
        showToast("Total: " + listComments.size());
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
}
