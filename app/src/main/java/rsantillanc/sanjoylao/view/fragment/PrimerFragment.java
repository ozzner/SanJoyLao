package rsantillanc.sanjoylao.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.custom.adapter.RecyclerViewAdapter;
import rsantillanc.sanjoylao.model.BanquetModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrimerFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private ArrayList<BanquetModel>banquets;
    private LinearLayoutManager mLinearLayoutManager;



    public PrimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View banquet = inflater.inflate(R.layout.fragment_primer, container, false);
        init(getActivity(),banquet);
        return banquet;
    }


    private void init(FragmentActivity activity,View view) {

        /*Init views*/
        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_banquets);

        /*Setup*/
        BanquetModel model = new BanquetModel();
        banquets = model.testData();
        mAdapter =  new RecyclerViewAdapter(banquets,activity);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(false);
        mLinearLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

    }


}
