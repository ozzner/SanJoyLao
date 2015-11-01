package rsantillanc.sanjoylao.ui.mvp.Main;

import android.app.Activity;

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


    /**
     * Carga las categorias desde el servidor y
     * las guarda en la base de datos del dispositivo no hay callbacks para este caso.
     */
    public void loadCategories(){
        iteractor.syncCategories(mainActivity.getApplicationContext());
    }

    public void savePreferences() {
        //delete this and paste in splash activity
        SJLPreferences preferences = new SJLPreferences(mainActivity);
        preferences.run();
    }
}
