package rsantillanc.sanjoylao.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLDates;
import rsantillanc.sanjoylao.util.SJLStrings;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private static UserModel user;
    private TextView tvLastLogin;
    private TextView tvSocialLogin;
    private TextView tvEmail;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(UserModel currentUser) {
        user = currentUser;
        AccountFragment fragment = new AccountFragment();
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
    }


    void setUpComponents() {
        tvEmail.setText(user.getEmail());

       if (user.getSocialLogin() == 0){

            tvSocialLogin.setText(Const.TAG_GOOGLE);
            tvSocialLogin.setTextColor(getActivity().getResources().getColor(R.color.google_plus));
        }else{
           tvSocialLogin.setText(Const.TAG_FACEBOOK);
        }

        tvLastLogin.setText(SJLDates.customDateConverter(user.getUpdatedAt(),SJLStrings.PARSE_DATE_FORMAT,SJLDates.FORMAT_DATE_GENERAL));
    }


}
