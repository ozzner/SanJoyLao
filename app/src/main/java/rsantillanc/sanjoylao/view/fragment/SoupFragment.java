package rsantillanc.sanjoylao.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.custom.adapter.ListViewAdapter;
import rsantillanc.sanjoylao.model.SoupModel;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;

/**
 * A simple {@link Fragment} subclass.
 */
public class SoupFragment extends Fragment implements View.OnClickListener, ListViewAdapter.OnItemClickListener {

    private static SoupFragment instance;
    private ListView mListView;
    private Button btOrder;

    private List<Object> soups;
    private ListViewAdapter mListViewAdpter;

    public static SoupFragment newInstance() {
        instance = new SoupFragment();
        return instance;
    }

    public static SoupFragment getIntance() {
        if (instance == null)
            instance = new SoupFragment();

        return instance;
    }

    public SoupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_soup, container, false);

        initComponents(v);
        return v;
    }

    private void initComponents(View v) {
        /*Get views*/
        mListView = (ListView) v.findViewById(R.id.lv_soups);

        /*Setup*/
        SoupModel oModel = new SoupModel();
        soups = oModel.testData();
        mListViewAdpter = new ListViewAdapter(getActivity(), soups, Const.SOUP);
        mListViewAdpter.setOnItemClickListener(this);
        mListView.setAdapter(mListViewAdpter);
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "Orden ok.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View v, int index) {
       int dvc = Android.getTypeDevice(getActivity());

        if (dvc > Const.PHONE_SCREEN){
            startFragment();
        }

    }

    protected void startFragment(){
        Fragment details = SoupDetailsFragment.getInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_details_soup, details);
        fragmentTransaction.commit();
    }
}
