package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    //Primitive methods
    public long insert(OrderTypeModel OrderType) {
        ContentValues cv = new ContentValues();
        cv.put(objectId, OrderType.getObjectId());
        cv.put(name, OrderType.getName());
        return db.insert(Tables.ORDER_TYPE, null, cv);
    }

    public List<OrderTypeModel> list() {
        Cursor cur = db.query(Tables.ORDER_TYPE, null, null,null, null, null, null);
        return loopOrdersType(cur,new ArrayList<OrderTypeModel>());
    }


    //{----Helpers----}

    private List<OrderTypeModel> loopOrdersType(Cursor cur, List<OrderTypeModel> types) {

        if (cur.moveToFirst())
            do {

                OrderTypeModel type = new OrderTypeModel();
                type.setObjectId(cur.getString(cur.getColumnIndex(objectId)));
                type.setName(cur.getString(cur.getColumnIndex(name)));
                types.add(type);

            } while (cur.moveToNext());

        if (!cur.isClosed()) {
            cur.close();
        }

        return types;
    }
}
