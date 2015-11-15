package rsantillanc.sanjoylao.ui.mvp.PlateDetail;

import android.app.Activity;

import java.io.Serializable;
import java.util.List;

import rsantillanc.sanjoylao.model.CommentModel;
import rsantillanc.sanjoylao.model.PlateModel;

/**
 * Created by RenzoD on 13/11/2015.
 */
public class PlateDetailPresenterImpl implements IPlateDetailPresenter, OnPlateDetailListener {

    private final Activity mActivity;
    IPlateDetailView mView;
    PlateDetailIteractorImpl iteractor;

    public PlateDetailPresenterImpl(IPlateDetailView mView,Activity activity) {
        iteractor = new PlateDetailIteractorImpl();
        this.mView = mView;
        this.mActivity = activity;
    }


    public void loadComments(Serializable model) {
        iteractor.loadCommentsByPlateId( mActivity,(PlateModel) model,this);
    }

    @Override
    public void onCommentsLoadSuccess(List<CommentModel> comments) {
        mView.hideLoader();
        mView.onCommentsSuccess(comments);
    }

    @Override
    public void onFailure(CharSequence sequence) {
        mView.hideLoader();
        mView.onError(sequence);
    }
}
