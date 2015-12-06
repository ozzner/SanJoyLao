package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import rsantillanc.sanjoylao.model.LocalRestaurantModel;
import rsantillanc.sanjoylao.model.ParseGeoPointModel;
import rsantillanc.sanjoylao.model.RestaurantModel;

/**
 * Created by RenzoD on 04/12/2015.
 */
public class LocalRestaurantDao extends BaseDao {

    private static final String SELECT = "Select * from " + Tables.LOCAL_RESTAURANT;

    private String deliveryAvailable = "deliveryAvailable";
    private String idRestaurant = "idRestaurant";
    private String locationLatitude = "locationLatitude";
    private String locationLongitude = "locationLongitude";
    private String address = "address";
    private String reservationAvailable = "reservationAvailable";

    //Database
    private SQLiteDatabase db;
    private Context _context;

    //Constructor
    public LocalRestaurantDao(Context c) {
        SJLDatabase mSJLDatabase = new SJLDatabase(c);
        this.db = mSJLDatabase.getWritableDatabase();
        this._context = c;
    }

    public long insert(LocalRestaurantModel local) {
        ContentValues cv = new ContentValues();
        cv.put(objectId, local.getObjectId());
        cv.put(createdAt, local.getCreatedAt());
        cv.put(updatedAt, local.getUpdatedAt());
        cv.put(deliveryAvailable, local.isDeliveryAvailable() ? 1 : 0);
        cv.put(idRestaurant, local.getRestaurant().getObjectId());
        cv.put(locationLongitude, local.getLocation().getLongitude());
        cv.put(locationLongitude, local.getLocation().getLongitude());
        cv.put(address, local.getAddress());
        cv.put(reservationAvailable, local.isReservationAvailable() ? 1 : 0);

        return db.insert(Tables.LOCAL_RESTAURANT, null, cv);
    }

    public int count() {
        Cursor cur = db.rawQuery(SELECT, null);
        return cur.getCount();
    }

    public List<LocalRestaurantModel> getAll() {
        Cursor cur = db.rawQuery(SELECT, null);
        return loopLocals(cur,new ArrayList<LocalRestaurantModel>());
    }


    //{Helpers}
    private List<LocalRestaurantModel> loopLocals(Cursor cur, List<LocalRestaurantModel> locals) {

        if (cur.moveToFirst())
            do {

                LocalRestaurantModel local = new LocalRestaurantModel();
                local.setObjectId(cur.getString(cur.getColumnIndex(objectId)));
                local.setCreatedAt(cur.getString(cur.getColumnIndex(createdAt)));
                local.setUpdatedAt(cur.getString(cur.getColumnIndex(updatedAt)));
                local.setAddress(cur.getString(cur.getColumnIndex(address)));
                local.setDeliveryAvailable((cur.getInt(cur.getColumnIndex(deliveryAvailable)) == 1 ? true : false));
                local.setReservationAvailable((cur.getInt(cur.getColumnIndex(reservationAvailable)) == 1 ? true : false));
                local.setLocation(makeGeoPoint(cur));
                local.setRestaurant(makeRestaurant());
                locals.add(local);

            } while (cur.moveToNext());

        if (!cur.isClosed()) {
            cur.close();
        }

        return locals;
    }

    private RestaurantModel makeRestaurant() {
        return new RestaurantDao(_context).get();
    }

    private ParseGeoPointModel makeGeoPoint(Cursor cur) {
        double latitude = cur.getDouble(cur.getColumnIndex(locationLatitude));
        double longitude = cur.getDouble(cur.getColumnIndex(locationLongitude));
        return new ParseGeoPointModel(latitude, longitude);
    }


}
