package rsantillanc.sanjoylao.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.custom.adapter.ListViewAdapter;
import rsantillanc.sanjoylao.model.RiceModel;
import rsantillanc.sanjoylao.util.Const;

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


    @Override
    public void onItemClick(View v, int index) {

    }
}
