package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import rsantillanc.sanjoylao.model.RestaurantModel;

/**
 * Created by RenzoD on 04/12/2015.
 */
public class RestaurantDao extends BaseDao {

    private static final String SELECT = "Select * from " + Tables.RESTAURANT;

    private String about = "about";
    private String centralCallNumber = "centralCallNumber";
    private String name = "name";
    private String ruc = "ruc";
    private String slogan = "slogan";

    //Database
    private SQLiteDatabase db;
    private Context _context;

    //Constructor
    public RestaurantDao(Context c) {
        SJLDatabase mSJLDatabase = new SJLDatabase(c);
        this.db = mSJLDatabase.getWritableDatabase();
        this._context = c;
    }

    public long insert(RestaurantModel restaurant) {
        ContentValues cv = new ContentValues();
        cv.put(objectId, restaurant.getObjectId());
        cv.put(createdAt, restaurant.getCreatedAt());
        cv.put(updatedAt, restaurant.getUpdatedAt());
        cv.put(about, restaurant.getAbout());
        cv.put(centralCallNumber, restaurant.getCentralCallNumber());
        cv.put(name, restaurant.getName());
        cv.put(ruc, restaurant.getRuc());
        cv.put(slogan, restaurant.getSlogan());

        return db.insert(Tables.RESTAURANT, null, cv);
    }

    public int count() {
        Cursor cur = db.rawQuery(SELECT, null);
        return cur.getCount();
    }

    public RestaurantModel get() {
        Cursor cur = db.query(Tables.RESTAURANT, null, null, null, null, null, null);

        RestaurantModel restaurant;

        if (cur.moveToFirst()) {
            restaurant = new RestaurantModel();
            restaurant.setObjectId(cur.getString(cur.getColumnIndex(objectId)));
            restaurant.setCreatedAt(cur.getString(cur.getColumnIndex(createdAt)));
            restaurant.setUpdatedAt(cur.getString(cur.getColumnIndex(updatedAt)));
            restaurant.setName(cur.getString(cur.getColumnIndex(name)));
            restaurant.setAbout(cur.getString(cur.getColumnIndex(about)));
            restaurant.setRuc(cur.getLong(cur.getColumnIndex(ruc)));
            restaurant.setCentralCallNumber(cur.getLong(cur.getColumnIndex(centralCallNumber)));
            restaurant.setSlogan(cur.getString(cur.getColumnIndex(slogan)));
        } else
            return null;

        return restaurant;
    }
}
