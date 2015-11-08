package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import rsantillanc.sanjoylao.model.OrderModel;

/**
 * Created by RenzoD on 06/11/2015.
 */
public class OrderDao {

    private static final String SELECT = "Select * from " + Tables.ORDERS;
    private static final String COMPARE = "=?";

    //Columns
    private String objectId = "objectId";
    private String idLocationDelivery = "idLocationDelivery";
    private String idOrderType = "idOrderType";
    private String idStatus = "idStatus";
    private String idPayment = "idPayment";
    private String idUser = "idUser";
    private String price = "price";
    private String createdAt = "createdAt";
    private String updatedAt = "updatedAt";


    //Database
    private SQLiteDatabase db;

    public OrderDao(Context c) {
        SJLDatabase mSJLDatabase = new SJLDatabase(c);
        this.db = mSJLDatabase.getWritableDatabase();
    }

    public int count() {
        Cursor cur = db.rawQuery(SELECT, null);
        return cur.getCount();
    }

    public long insert(OrderModel order) {
        ContentValues cv = new ContentValues();
        cv.put(objectId, order.getObjectId());
        if (order.getLocationDelivery() != null)
            cv.put(idLocationDelivery, order.getLocationDelivery().getObjectId());
        if (order.getOrderType() != null)
            cv.put(idOrderType, order.getOrderType().getObjectId());
        if (order.getStatus() != null)
            cv.put(idStatus, order.getStatus().getObjectId());
        if (order.getPyment() != null)
            cv.put(idPayment, order.getPyment().getObjectId());
        if (order.getUser() != null)
            cv.put(idUser, order.getUser().getObjectId());
        cv.put(price, order.getPrice());
        cv.put(createdAt, order.getCreatedAt());
        cv.put(updatedAt, order.getUpdatedAt());
        return db.insert(Tables.ORDERS, null, cv);
    }

    public OrderModel getActiveOrderIfExist(String statusID) {
        Cursor cur = db.query(Tables.ORDERS, null, idStatus + COMPARE, new String[]{statusID}, null, null, null);
        OrderModel order;

        if (cur.moveToFirst()) {
            order = new OrderModel();
            order.setObjectId(cur.getString(cur.getColumnIndex(objectId)));
            order.setCreatedAt(cur.getString(cur.getColumnIndex(createdAt)));
            order.setUpdatedAt(cur.getString(cur.getColumnIndex(updatedAt)));
            order.setPrice(cur.getDouble(cur.getColumnIndex(price)));
        } else
            order = null;

        return order;
    }

    public boolean checkActiveOrder(String statusID) {
        Cursor cur = db.query(Tables.ORDERS, null, idStatus + COMPARE, new String[]{statusID}, null, null, null);
        return cur.moveToFirst();
    }
}
