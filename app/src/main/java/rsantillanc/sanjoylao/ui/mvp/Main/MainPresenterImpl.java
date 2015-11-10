package rsantillanc.sanjoylao.ui.mvp.Main;

import android.app.Activity;
import android.widget.ImageView;

import rsantillanc.sanjoylao.storage.sp.SJLPreferences;

/**
 * Created by rsantillanc on 20/10/2015.
 */
public class MainPresenterImpl {

    private IMainView mView;
    private MainIteractorImpl iteractor;
    private Activity mainActivity;

    public MainPresenterImpl(IMainView mView,Activity activity) {
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
    public void loadCategories(){
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

    public void loadOrders() {
        iteractor.syncOrders(mainActivity.getApplicationContext());
    }
}
