package rsantillanc.sanjoylao.ui.mvp.PlateDetail;

import java.util.List;

import rsantillanc.sanjoylao.model.CommentModel;

/**
 * Created by RenzoD on 13/11/2015.
 */
public interface IPlateDetailView {
    void onCommentsSuccess(List<CommentModel> listComments);
    void onError(CharSequence cs);
    void showLoader();
    void hideLoader();
    void hideAddNew();
    void showAddNew();
    void showMessage(String s);
    void addComment(CommentModel newComment);
}
