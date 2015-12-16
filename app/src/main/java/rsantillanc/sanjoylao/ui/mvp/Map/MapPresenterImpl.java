package rsantillanc.sanjoylao.ui.mvp.Map;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import rsantillanc.sanjoylao.model.LocalRestaurantModel;
import rsantillanc.sanjoylao.model.OrderTypeModel;
import rsantillanc.sanjoylao.ui.activity.MapActivity;

/**
 * Created by Computer on 15/12/2015.
 */
public class MapPresenterImpl implements IMapPresenter{

    MapActivity view;
    MapIteractorImpl iteractor;


    public MapPresenterImpl(MapActivity mapview) {
        this.view = mapview;
        this.iteractor = new MapIteractorImpl(this.view);
    }


    @Override
    public void findLocals() {
       new AsyncTaskProcess().execute();
    }


    /**
     * AsyncTask
     */

    class AsyncTaskProcess extends AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... objects) {
            return iteractor.retrieveLocals(view);
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);

            if (result instanceof ArrayList<?>) {
                Object object = ((ArrayList<?>) result).get(0);

                //Check and return
                if (object instanceof LocalRestaurantModel) {
                    view.printLocals(((ArrayList<LocalRestaurantModel>) result));
                }
            }

        }
    }
}
