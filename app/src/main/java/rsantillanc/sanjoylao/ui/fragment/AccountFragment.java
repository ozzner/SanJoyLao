package rsantillanc.sanjoylao.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.ui.mvp.Account.AccountPresenterImpl;
import rsantillanc.sanjoylao.ui.mvp.Account.IAccountView;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLDates;
import rsantillanc.sanjoylao.util.SJLStrings;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class AccountFragment extends Fragment implements IAccountView, View.OnClickListener {

    //public & static
    private static UserModel user;
    public boolean isEnabled = false;

    //[Views]
    private TextView tvLastLogin;
    private TextView tvSocialLogin;
    private TextView tvEmail;
    private ImageView ivActionEdit;
    private EditText etIdentity;
    private EditText etPhoneNumber;
    private EditText etBirthday;


    private AccountPresenterImpl presenter;
    private EditText[] fields;


    @SuppressLint("ValidFragment")
    public AccountFragment(UserModel currentUser) {
        user = currentUser;
        presenter = new AccountPresenterImpl(getActivity(), this);
    }

    public static AccountFragment newInstance(UserModel currentUser) {
        user = currentUser;
        AccountFragment fragment = new AccountFragment(user);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_account, container, false);
        initUIElements(inflate);
        setUpComponents();
        return inflate;
    }

    private void initUIElements(View inflate) {
        tvLastLogin = (TextView) inflate.findViewById(R.id.tv_account_lastlogin);
        tvSocialLogin = (TextView) inflate.findViewById(R.id.tv_account_sociallogin);
        tvEmail = (TextView) inflate.findViewById(R.id.tv_account_email);
        ivActionEdit = (ImageView) inflate.findViewById(R.id.iv_profile_edit);
        etIdentity = (EditText) inflate.findViewById(R.id.et_profile_identity);
        etPhoneNumber = (EditText) inflate.findViewById(R.id.et_profile_phone);
        etBirthday = (EditText) inflate.findViewById(R.id.et_profile_birthday);
    }


    void setUpComponents() {
        //Listeners
        ivActionEdit.setOnClickListener(this);

        buildProfileData();

        fields = new EditText[]{etIdentity, etPhoneNumber, etBirthday};
    }

    private void buildProfileData() {
        //Access
        tvEmail.setText(user.getEmail());

        if (user.getSocialLogin() == 0) {
            tvSocialLogin.setText(Const.TAG_GOOGLE);
            tvSocialLogin.setTextColor(getActivity().getResources().getColor(R.color.google_plus));
        } else {
            tvSocialLogin.setText(Const.TAG_FACEBOOK);
        }

        tvLastLogin.setText(SJLDates.customDateConverter(user.getUpdatedAt(), SJLStrings.PARSE_DATE_FORMAT, SJLDates.FORMAT_DATE_GENERAL));

        //Reservations
        etIdentity.setText(String.valueOf(user.getIdentificationDocument() == 0 ? Const.EMPTY : user.getIdentificationDocument()));
        etPhoneNumber.setText(String.valueOf(user.getPhoneNumber() == 0 ? Const.EMPTY : user.getPhoneNumber()));
        etBirthday.setText(String.valueOf(user.getBirthday()));


    }


    //[IAccountView]

    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showMessage(CharSequence message) {

    }

    //[Click]
    @Override
    public void onClick(View view) {
        if (view instanceof ImageView) {
            enabledEditing();
            toggleColor();
        } else if (view instanceof EditText) {

            switch (view.getId()) {
                case R.id.et_profile_birthday:
                    presenter.makeCalendar();
                    break;
            }
        }
    }

    private void toggleColor() {
        if (isEnabled)
            ivActionEdit.setColorFilter(getResources().getColor(R.color.colorPrimary));
        else
            ivActionEdit.setColorFilter(getResources().getColor(R.color.colorAccent));
    }

    private void enabledEditing() {
        isEnabled = !isEnabled;
        etIdentity.setEnabled(isEnabled);
        etPhoneNumber.setEnabled(isEnabled);
        etBirthday.setEnabled(isEnabled);
    }

    public EditText[] getFields() {
        return fields;
    }
}
