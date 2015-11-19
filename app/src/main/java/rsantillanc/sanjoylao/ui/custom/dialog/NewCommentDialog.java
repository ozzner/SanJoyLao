package rsantillanc.sanjoylao.ui.custom.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import rsantillanc.sanjoylao.R;

/**
 * Created by RenzoD on 18/11/2015.
 */
public class NewCommentDialog extends DialogFragment implements View.OnClickListener {

    private OnNewCommentListener listener;
    private EditText etComment;
    private Button btCancel;
    private Button btSend;
    private boolean comment;


    public NewCommentDialog() {
    }



    public static NewCommentDialog newInstance() {
        return new NewCommentDialog();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_comment, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        initUIElements(view);
        return view;
    }

    private void initUIElements(View view) {
        etComment = (EditText) view.findViewById(R.id.et_input_comment);
        btCancel = (Button) view.findViewById(R.id.bt_cancel);
        btSend = (Button) view.findViewById(R.id.bt_send);

        //Set listener
        btCancel.setOnClickListener(this);
        btSend.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.equals(btCancel))
            getDialog().cancel();
        else {
            if (getComment().isEmpty())
                listener.onError(getActivity().getString(R.string.error_empty));
            else
                listener.onClickSendButton(getComment());
        }

    }

    private String getComment() {
        return etComment.getText().toString().trim();
    }

    public void setOnNewCommentListener(OnNewCommentListener listener) {
        this.listener =listener;
    }


    public interface OnNewCommentListener {
        void onClickSendButton(String comment);
        void onError(CharSequence sc);
    }

}
