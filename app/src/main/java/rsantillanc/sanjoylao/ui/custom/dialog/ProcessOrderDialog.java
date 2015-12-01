package rsantillanc.sanjoylao.ui.custom.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import rsantillanc.sanjoylao.R;

/**
 * Created by RenzoD on 18/11/2015.
 */
public class ProcessOrderDialog extends DialogFragment implements View.OnClickListener {

    //Views
    private Button btCancel;
    private Button btSend;
    private AppCompatRadioButton appRbDelivery;
    private AppCompatRadioButton appRbBooking;

    //Properties
    OnProcessOrderClickListener listener;


    public ProcessOrderDialog() {
    }


    public static ProcessOrderDialog newInstance() {
        return new ProcessOrderDialog();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_process_order, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        initUIElements(view);
        return view;
    }

    private void initUIElements(View view) {
        appRbBooking = (AppCompatRadioButton) view.findViewById(R.id.app_rb_types_1);
        appRbDelivery = (AppCompatRadioButton) view.findViewById(R.id.app_rb_types_2);
        btCancel = (Button) view.findViewById(R.id.bt_cancel);
        btSend = (Button) view.findViewById(R.id.bt_send);


        //Set listener
        appRbDelivery.setOnClickListener(this);
        appRbBooking.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        btSend.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view instanceof Button) {
            switch (view.getId()) {
                case R.id.bt_cancel:
                    getDialog().cancel();
                    break;
                case R.id.bt_send:
                    getDialog().cancel();
                    listener.onClickSendButton();
                    break;
            }

        }

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.app_rb_types_1:
                if (checked)
                    Toast.makeText(getActivity(),appRbBooking.getText().toString(),Toast.LENGTH_LONG).show();
                    break;
            case R.id.app_rb_types_2:
                if (checked)
                    Toast.makeText(getActivity(),appRbDelivery.getText().toString(),Toast.LENGTH_LONG).show();
                break;
        }
    }

    public OnProcessOrderClickListener getListener() {
        return listener;
    }

    public void setListener(OnProcessOrderClickListener listener) {
        this.listener = listener;
    }

    public interface OnProcessOrderClickListener {
        void onClickSendButton();

        void onError(CharSequence sc);
    }

}
