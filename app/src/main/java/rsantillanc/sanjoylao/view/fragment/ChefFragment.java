package rsantillanc.sanjoylao.view.fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.custom.adapter.ListViewAdapter;
import rsantillanc.sanjoylao.model.ChefModel;
import rsantillanc.sanjoylao.util.Const;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChefFragment extends Fragment implements ListViewAdapter.OnItemClickListener {

    private static ChefFragment instance;
    private ListView mListView;
    private List<Object> chefs;
    private ListViewAdapter mListViewAdpter;

    public ChefFragment() {
        // Required empty public constructor
    }


    public static ChefFragment newInstance() {
        instance =  new ChefFragment();
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.fragment_chef, container, false);
        initComponents(vi);
        return vi;
    }

    private void initComponents(View vi) {
           /*Get views*/
        mListView = (ListView) vi.findViewById(R.id.lv_chef);

        /*Setup*/
        ChefModel oModel = new ChefModel();
        chefs = oModel.testData();
        mListViewAdpter = new ListViewAdapter(getActivity(), chefs, Const.CHEF);
        mListViewAdpter.setOnItemClickListener(this);
        mListView.setAdapter(mListViewAdpter);
    }


    @Override
    public void onItemClick(View v, int index) {

    }
}
