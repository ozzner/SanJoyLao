package rsantillanc.sanjoylao.ui.mvp.PlateDetail;

import java.util.List;

import rsantillanc.sanjoylao.model.CommentModel;

/**
 * Created by RenzoD on 15/11/2015.
 */
public interface OnPlateDetailListener {
    void onCommentsLoadSuccess(List<CommentModel> comments);
    void onFailure(CharSequence sequence);
    void onCommentSendSuccess(String s);
    void onCommentEmpty();
    void addRecentlyComment(CommentModel newComment);
}
