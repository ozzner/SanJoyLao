package rsantillanc.sanjoylao.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.BanquetModel;
import rsantillanc.sanjoylao.ui.custom.adapter.ListViewAdapter;
import rsantillanc.sanjoylao.ui.custom.adapter.RecyclerViewBanquetAdapter;
import rsantillanc.sanjoylao.ui.popup.DetailsOptionsPopup;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlateFragment extends Fragment implements ListViewAdapter.OnItemClickListener{


    private static PlateFragment instance;
    private RecyclerView mRecycler;
    private List<Object> banquets;
    private ListViewAdapter mListViewAdpter;
    private RecyclerViewBanquetAdapter adapter;


    public PlateFragment() {

    }


    public static PlateFragment newInstance() {
            instance =  new PlateFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.fragment_plate, container, false);
        initComponents(vi);
        return vi;
    }


    private void initComponents(View vi) {
         /*Get views*/
        mRecycler = (RecyclerView) vi.findViewById(R.id.rv_plates);

        /*Setup*/
        banquets = new BanquetModel().dummyBanquets();
        adapter =  new RecyclerViewBanquetAdapter(banquets, getActivity());
        mRecycler.setAdapter(adapter);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(),3));



//       mRecycler.setHasFixedSize(true);



    }


    public void onItemClick(View v, int index) {
        if (v.getId() == R.id.iv_rice){
            Toast.makeText(getActivity(),"Amplia imagen.",Toast.LENGTH_LONG).show();
        }else if (v.getId() == R.id.bt_rice_order){
            Toast.makeText(getActivity(),"Pedido realizado!.",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(),"Abre detalles!.",Toast.LENGTH_LONG).show();;
        }
//        openInfoSoup();
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

                    FragmentManager fm = getFragmentManager();
                    if (fm.getBackStackEntryCount() > 0) {
                        Log.i("MainActivity", "popping backstack");
                        fm.popBackStack();
                    } else {

                    }

//                    Fragment gotToFront = new MainFragment(new MainFragment.OnLoadSuccess() {
//                        @Override
//                        public void viewloaded() {
//
//                        }
//                    },true);
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragments_content, gotToFront);
//                    fragmentTransaction.commit();

                    return true;
                }
                return false;
            }
        });
    }
}
