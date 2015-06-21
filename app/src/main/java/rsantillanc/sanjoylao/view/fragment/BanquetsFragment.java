package rsantillanc.sanjoylao.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.view.activity.OptionsGridActivity;
import rsantillanc.sanjoylao.view.activity.OptionsListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BanquetsFragment extends Fragment {

    private static BanquetsFragment instance;
    private RecyclerView mRecyclerView;
    private RecyclerViewBanquetAdapter mAdapter;
    private ArrayList<Object> banquets;
    private LinearLayoutManager mLinearLayoutManager;
    private int typeDevice;


    public BanquetsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View banquet = inflater.inflate(R.layout.fragment_banquets, container, false);
        init(getActivity(), banquet);
        return banquet;
    }


    private void init(FragmentActivity activity, View view) {

        /*Init views*/
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_banquets);

        /*Setup*/
        BanquetModel model = new BanquetModel();
        banquets = model.testData();
        mAdapter = new RecyclerViewBanquetAdapter(banquets, activity);
        mAdapter.setOnItemClickListener(new RecyclerViewBanquetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                //Obtain type of device
                typeDevice = Android.getTypeDevice(getActivity());
                Bundle bundle = new Bundle();
                bundle.putSerializable(Const.TAG_BANQUET, (BanquetModel) banquets.get(index));

                if (typeDevice > Const.PHONE_SCREEN) {

                    Fragment details = BanquetDetailsFragment.getInstance();
                    details.setArguments(bundle);
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction first = manager.beginTransaction();
                    first.replace(R.id.container_details_banquet,details);
                    first.commit();
                } else {

                    Intent in;
                    if (((BanquetModel) banquets.get(index)).isFlagOptions())
                        in = new Intent(getActivity(), OptionsListActivity.class);
                    else
                        in = new Intent(getActivity(), OptionsGridActivity.class);

                    in.putExtras(bundle);
                    startActivity(in);
                }

                Toast.makeText(getActivity(), "opening... position: " + index, Toast.LENGTH_LONG).show();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(false);
        mLinearLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

    }




        public static BanquetsFragment newInstance() {
                instance =  new BanquetsFragment();

            return instance;
        }

}
