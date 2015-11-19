package rsantillanc.sanjoylao.ui.mvp.PlateDetail;

import android.app.Activity;

import java.io.Serializable;
import java.util.List;

import rsantillanc.sanjoylao.model.CommentModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.ui.custom.dialog.NewCommentDialog;

/**
 * Created by RenzoD on 13/11/2015.
 */
public class PlateDetailPresenterImpl implements IPlateDetailPresenter, OnPlateDetailListener, NewCommentDialog.OnNewCommentListener {

    Activity mActivity;
    IPlateDetailView mView;
    PlateDetailIteractorImpl iteractor;
    NewCommentDialog commentDialog;

    private PlateModel plate;
    private UserModel user;


    public PlateDetailPresenterImpl(IPlateDetailView mView, Activity activity) {
        iteractor = new PlateDetailIteractorImpl();
        this.mView = mView;
        this.mActivity = activity;
    }


    public void loadComments(Serializable model) {
        mView.showLoader();
        iteractor.loadCommentsByPlateId(mActivity, (PlateModel) model, this);
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

    @Override
    public void onCommentSendSuccess(String s) {
        commentDialog.getDialog().cancel();
        mView.showMessage(s);
    }

    public void showAlertDialogComment(Serializable plate, Serializable user) {
        this.plate = (PlateModel) plate;
        this.user = (UserModel) user;

        commentDialog = new NewCommentDialog();
        commentDialog.setOnNewCommentListener(this);
        commentDialog.show(mActivity.getFragmentManager(), "alert_comment");
    }


    //{New comment listener}
    @Override
    public void onClickSendButton(String comment) {
        iteractor.sendComment(plate, user, comment, this);
    }

    @Override
    public void onError(CharSequence sc) {
        mView.onError(sc);
    }
}
