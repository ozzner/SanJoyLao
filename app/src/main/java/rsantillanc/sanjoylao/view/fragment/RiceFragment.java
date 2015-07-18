package rsantillanc.sanjoylao.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.custom.adapter.ListViewAdapter;
import rsantillanc.sanjoylao.model.RiceModel;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.view.popup.DetailsOptionsPopup;

/**
 * A simple {@link Fragment} subclass.
 */
public class RiceFragment extends Fragment implements ListViewAdapter.OnItemClickListener{


    private static RiceFragment instance;
    private ListView mListView;
    private List<Object> rices;
    private ListViewAdapter mListViewAdpter;



    public RiceFragment() {

    }


    public static RiceFragment newInstance() {
            instance =  new RiceFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.fragment_rice, container, false);
        initComponents(vi);
        return vi;
    }


    private void initComponents(View vi) {
         /*Get views*/
        mListView = (ListView) vi.findViewById(R.id.lv_rices);

        /*Setup*/
        RiceModel oModel = new RiceModel();
        rices = oModel.testData();
        mListViewAdpter = new ListViewAdapter(getActivity(), rices, Const.RICE);
        mListViewAdpter.setOnItemClickListener(this);
        mListView.setAdapter(mListViewAdpter);
    }


    public void onItemClick(View v, int index) {
        openInfoSoup();
    }

    private void openInfoSoup() {
        Intent in = new Intent(getActivity(), DetailsOptionsPopup.class);
        startActivity(in);
    }

    protected void startFragment() {
        Fragment details = SoupDetailsFragment.getInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_details_soup, details);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        backToFrontPage();
    }

    private void backToFrontPage() {

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    Fragment gotToFront = MainFragment.newInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, gotToFront);
                    fragmentTransaction.commit();

                    return true;
                }
                return false;
            }
        });
    }
}
