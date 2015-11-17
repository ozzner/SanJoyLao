package rsantillanc.sanjoylao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.CommentModel;
import rsantillanc.sanjoylao.ui.custom.adapter.RecyclerCommentAdapter;
import rsantillanc.sanjoylao.ui.mvp.PlateDetail.IPlateDetailView;
import rsantillanc.sanjoylao.ui.mvp.PlateDetail.PlateDetailPresenterImpl;
import rsantillanc.sanjoylao.util.Const;

public class PlateDetailActivity extends BaseActivity implements IPlateDetailView {

    //View
    private Toolbar toolbar;
    private ProgressBar pbLoader;
    private RecyclerView rcvComments;

    //Runtime
    private RecyclerCommentAdapter mAdapter;



    private PlateDetailPresenterImpl mPresenter;


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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter.loadComments(getIntent().getExtras().getSerializable(Const.EXTRA_PLATE_DETAIL));
    }

    private void initUIElements() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pbLoader = (ProgressBar) findViewById(R.id.pg_plate_detail_loader);
        rcvComments = (RecyclerView)findViewById(R.id.rcv_plates_detail_comments);
    }

    private void init() {
        mPresenter = new PlateDetailPresenterImpl(this, this);
    }

    //---{CallBacks IView}
    @Override
    public void onCommentsSuccess(List<CommentModel> listComments) {
        mAdapter = new RecyclerCommentAdapter(listComments,getApplicationContext());
        rcvComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcvComments.setHasFixedSize(true);
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
}
