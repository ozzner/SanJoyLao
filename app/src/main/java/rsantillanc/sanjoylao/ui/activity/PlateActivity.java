package rsantillanc.sanjoylao.ui.activity;

import android.os.Bundle;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.ui.mvp.Plate.IPlateView;
import rsantillanc.sanjoylao.ui.mvp.Plate.PlatePresenterImpl;
import rsantillanc.sanjoylao.util.Const;

public class PlateActivity extends BaseActivity implements IPlateView {

    //Runtime
    PlatePresenterImpl mpresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate);

        //Init
        init(getIntent().getExtras());

        //Views
        initUIElements();

        //Setups
        initSetUpsElements();
    }

    private void init(Bundle extras) {
        String categoryID = extras.getString(Const.EXTRA_CATEGORY_ID);
        mpresenter = new PlatePresenterImpl(this, this);
        mpresenter.loadPlatesByCategory(categoryID);
    }


    private void initSetUpsElements() {

    }

    private void initUIElements() {

    }


    // [IView]

    @Override
    public void listPlatesByCategory(List<PlateModel> plates) {
    }

    @Override
    public void goToPlateDetail(PlateModel plate) {

    }
}
