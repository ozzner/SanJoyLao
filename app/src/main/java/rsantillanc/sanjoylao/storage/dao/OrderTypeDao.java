package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import rsantillanc.sanjoylao.model.OrderTypeModel;

/**
 * Created by RenzoD on 05/11/2015.
 */
public class OrderTypeDao {

    //Const
    private static final String SELECT = "Select * from " + Tables.ORDER_TYPE;
    private static final String COMPARE = "=?";

    //Columns
    private String objectId = "objectId";
    private String name = "name";

    //Database
    private SQLiteDatabase db;

    public OrderTypeDao(Context c) {
        SJLDatabase mSJLDatabase = new SJLDatabase(c);
        this.db = mSJLDatabase.getWritableDatabase();
    }

    public int count() {
        Cursor cur = db.rawQuery(SELECT, null);
        return cur.getCount();
    }

    public long insert(OrderTypeModel OrderType) {
        ContentValues cv = new ContentValues();
        cv.put(objectId, OrderType.getObjectId());
        cv.put(name, OrderType.getName());
        return db.insert(Tables.ORDER_TYPE, null, cv);
    }

}
