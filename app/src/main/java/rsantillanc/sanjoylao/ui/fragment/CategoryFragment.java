package rsantillanc.sanjoylao.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.CategoryModel;
import rsantillanc.sanjoylao.ui.activity.PlateActivity;
import rsantillanc.sanjoylao.ui.custom.adapter.RecyclerCategoryAdapter;
import rsantillanc.sanjoylao.ui.mvp.Category.CategoryPresenterImpl;
import rsantillanc.sanjoylao.ui.mvp.Category.ICategoryView;
import rsantillanc.sanjoylao.ui.mvp.Main.IMainView;
import rsantillanc.sanjoylao.ui.mvp.Main.MainPresenterImpl;
import rsantillanc.sanjoylao.util.Const;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements RecyclerCategoryAdapter.OnItemClickListener,
        ICategoryView {

    //View
    private RecyclerView mRecycler;
    private RecyclerCategoryAdapter adapter;

    //Object
    private List<CategoryModel> categories;
    private static CategoryFragment instance;
    private CategoryPresenterImpl mPresenter;


    public CategoryFragment() {

    }


    public static CategoryFragment newInstance() {
        instance = new CategoryFragment();
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
        mPresenter = new CategoryPresenterImpl(this, getActivity());

        /*Setup*/
        categories = mPresenter.getCategoryList();
        adapter = new RecyclerCategoryAdapter(categories, getActivity());
        adapter.setOnItemClickListener(this);
        mRecycler.setAdapter(adapter);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));

//       mRecycler.setHasFixedSize(true);
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
                    MainPresenterImpl presenter = new MainPresenterImpl((IMainView) getActivity(),getActivity());
                    presenter.displayFragment(MainPresenterImpl.HOME);

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void loadImage() {


    }

    @Override
    public void loadData() {

    }


    @Override
    public void onItemClick(CategoryModel category) {
        goToPlateActivity(category);
    }

    private void goToPlateActivity(CategoryModel category) {
        Intent plate = new Intent(getActivity(), PlateActivity.class);
        plate.putExtra(Const.EXTRA_CATEGORY_ID, category.getObjectId());
        plate.putExtra(Const.EXTRA_CATEGORY_NAME, category.getName());
        startActivity(plate);
    }
}
