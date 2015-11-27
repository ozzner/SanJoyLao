package rsantillanc.sanjoylao.ui.mvp.PlateDetail;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;

import java.io.Serializable;
import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.CommentModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.ui.activity.ViewerActivity;
import rsantillanc.sanjoylao.ui.custom.dialog.NewCommentDialog;
import rsantillanc.sanjoylao.ui.custom.dialog.SJLAlertDialog;

/**
 * Created by RenzoD on 13/11/2015.
 */
public class PlateDetailPresenterImpl implements IPlateDetailPresenter, OnPlateDetailListener, NewCommentDialog.OnNewCommentListener {

    Activity mActivity;
    IPlateDetailView view;
    PlateDetailIteractorImpl iteractor;
    NewCommentDialog commentDialog;

    private PlateModel plate;
    private UserModel user;


    public PlateDetailPresenterImpl(IPlateDetailView mView, Activity activity) {
        iteractor = new PlateDetailIteractorImpl();
        this.view = mView;
        this.mActivity = activity;
    }


    public void loadComments(Serializable model) {
        view.showLoader();
        iteractor.loadCommentsByPlateId(mActivity, (PlateModel) model, this);
    }

    @Override
    public void onCommentsLoadSuccess(List<CommentModel> comments) {
        view.hideLoader();
        view.hideAddNew();
        view.onCommentsSuccess(comments);
    }

    @Override
    public void onFailure(CharSequence sequence) {
        view.hideLoader();
        view.onError(sequence);
    }

    @Override
    public void onCommentSendSuccess(String s) {
        commentDialog.getDialog().cancel();
        view.showMessage(s);
    }

    @Override
    public void onCommentEmpty() {
        view.hideLoader();
        view.showAddNew();
    }

    @Override
    public void addRecentlyComment(CommentModel newComment) {
//        iteractor.saveComment(newComment);
        view.addComment(newComment);
    }

    public void showAlertDialogComment(Serializable plate, Serializable user) {
        this.plate = (PlateModel) plate;
        this.user = (UserModel) user;

        commentDialog = new NewCommentDialog();
        commentDialog.setOnNewCommentListener(this);
        commentDialog.show(mActivity.getFragmentManager(), "alert_comment");
    }

    public void showIngredients(PlateModel serializable) {
        String HTML_BODY =
                "              <p style=\"text-align: justify;\">" +
                        "        <h4><font color='#D32F2F'>" + mActivity.getString(R.string.label_description) + "</font></h4>" +
                        "        <font color='#607D8B'>" +
                        "        La sopa wantán es una sopa china, hecha a base de caldo de pollo, carne de pollo, cerdo y Wantán.<br><br>" +

                        "        Usualmente lleva tres o cuatro wantanes y se sirve con cebolla china.\n<br><br>" +
                        "        Esta sopa también puede llevar col y fideos chinos, además de langostinos y por" +
                        "        lo general se le agregan sillao (salsa de soya).<br><br>" +

                        "        Es usual consumirla previa a un plato de fondo como Chow mein o arroz frito.\n" +
                        "        </font><br><br><br>" +
                        "        <h4><font color='#D32F2F'>" + mActivity.getString(R.string.label_ingredients) + "</font></h4>" +
                        "        Wantan, pollo, langostino, chancho, pato, huevo de codorniz." +
                        "        </p>" +
                        "";
        serializable.setIngredients(Html.fromHtml(HTML_BODY));
        SJLAlertDialog.showCustomAlert(mActivity, serializable);
    }

    public void openImage() {
        Intent viewer = new Intent(mActivity, ViewerActivity.class);
        mActivity.startActivity(viewer);
    }


    //{New comment listener}
    @Override
    public void onClickSendButton(String comment) {
        iteractor.sendComment(plate, user, comment, this);
    }

    @Override
    public void onError(CharSequence sc) {
        view.onError(sc);
    }


}
