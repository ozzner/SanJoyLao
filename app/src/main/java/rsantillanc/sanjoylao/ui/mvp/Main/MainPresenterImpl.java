package rsantillanc.sanjoylao.ui.mvp.Main;

import android.app.Activity;
import android.widget.ImageView;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.storage.sp.SJLPreferences;
import rsantillanc.sanjoylao.ui.fragment.CategoryFragment;
import rsantillanc.sanjoylao.ui.fragment.HomeFragment;

/**
 * Created by rsantillanc on 20/10/2015.
 */
public class MainPresenterImpl {
    //Constants
    public static final int ENTREE = R.id.nav_main_entree;
    public static final int SOUP = R.id.nav_main_soup;
    public static final int CATEGORY = R.id.nav_main_category;
    public static final int CHEF = R.id.nav_main_chef;
    public static final int BANQUET = R.id.nav_main_banquet;
    public static final int DRINKS = R.id.nav_main_drink;
    public static final int CENTRAL = R.id.nav_main_call_center;
    public static final int HOME = -19071989;


    private IMainView mView;
    private MainIteractorImpl iteractor;
    private Activity mainActivity;

    public MainPresenterImpl(IMainView mView, Activity activity) {
        this.mView = mView;
        this.iteractor = new MainIteractorImpl();
        this.mainActivity = activity;
    }

    public void savePreferences() {
        //delete this and paste in splash activity
        SJLPreferences preferences = new SJLPreferences(mainActivity);
        preferences.run();
    }

    /**
     * Carga las categorias desde el servidor y
     * las guarda en la base de datos del dispositivo no hay callbacks para este caso.
     */
    public void loadCategories() {
        iteractor.syncCategories(mainActivity.getApplicationContext());
    }

    public void loadSizes() {
        iteractor.syncSizes(mainActivity.getApplicationContext());
    }

    public void loadProfileImage(String urlProfileImage, ImageView imageView) {
        iteractor.getProfileImage(mainActivity, imageView, urlProfileImage);
    }

    public void loadPlatesSize() {
        iteractor.syncPlateSize(mainActivity.getApplicationContext());
    }

    public void loadPlate() {
        iteractor.syncPlates(mainActivity.getApplicationContext());
    }

    public void loadOrderType() {
        iteractor.syncOrderType(mainActivity.getApplicationContext());
    }

    public void loadStatus() {
        iteractor.syncStatus(mainActivity.getApplicationContext());
    }

    public void loadOrders(String userID) {
        iteractor.syncOrders(mainActivity.getApplicationContext(), userID);
    }

    public void displayFragment(int id) {

        switch (id) {

            case ENTREE:
                mView.displayFragment(HomeFragment.newInstance(), false);
                mView.updateSubtitle(mainActivity.getString(R.string.item_title_entree));
                mView.collapse(true);
                break;
            case SOUP:
                mView.displayFragment(HomeFragment.newInstance(), false);
                mView.updateSubtitle(mainActivity.getString(R.string.item_title_soup));
                mView.collapse(true);
                break;
            case CATEGORY:
                mView.displayFragment(CategoryFragment.newInstance(),true);
                mView.updateSubtitle(mainActivity.getString(R.string.item_title_category));
                mView.collapse(false);
                break;
            case CHEF:
                mView.displayFragment(HomeFragment.newInstance(), false);
                mView.updateSubtitle(mainActivity.getString(R.string.item_title_chef));
                mView.collapse(true);
                break;
            case BANQUET:
                mView.displayFragment(HomeFragment.newInstance(), false);
                mView.updateSubtitle(mainActivity.getString(R.string.item_title_banquet));
                mView.collapse(true);
                break;
            case DRINKS:
                mView.displayFragment(HomeFragment.newInstance(), false);
                mView.updateSubtitle(mainActivity.getString(R.string.item_title_drinks));
                mView.collapse(true);
                break;
            case CENTRAL:
                mView.displayFragment(HomeFragment.newInstance(), false);
                mView.updateSubtitle(mainActivity.getString(R.string.item_title_category));
                mView.collapse(true);
                break;
            default:
                mView.displayFragment(HomeFragment.newInstance(), false);
                mView.updateSubtitle(mainActivity.getString(R.string.home));
                mView.collapse(true);
                break;
        }

        mView.closeMenu();
        mView.markItemSelected(id);
    }
}
