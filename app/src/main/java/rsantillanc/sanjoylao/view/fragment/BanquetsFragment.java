package rsantillanc.sanjoylao.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.custom.adapter.RecyclerViewBanquetAdapter;
import rsantillanc.sanjoylao.model.BanquetModel;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.view.activity.OptionsGridActivity;
import rsantillanc.sanjoylao.view.activity.OptionsListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BanquetsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerViewBanquetAdapter mAdapter;
    private ArrayList<BanquetModel>banquets;
    private LinearLayoutManager mLinearLayoutManager;



    public BanquetsFragment() {
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
        mAdapter =  new RecyclerViewBanquetAdapter(banquets,activity);
        mAdapter.setOnItemClickListener(new RecyclerViewBanquetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {

                Bundle bundle = new Bundle();
                bundle.putSerializable(Const.TAG_BANQUET,banquets.get(index));
                Intent in ;

                if (banquets.get(index).isFlagOptions())
                    in= new Intent(getActivity(), OptionsListActivity.class);
                else
                    in= new Intent(getActivity(), OptionsGridActivity.class);

                in.putExtras(bundle);
                startActivity(in);

                Toast.makeText(getActivity(),"opening... position: " + index,Toast.LENGTH_LONG).show();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(false);
        mLinearLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

    }



}
