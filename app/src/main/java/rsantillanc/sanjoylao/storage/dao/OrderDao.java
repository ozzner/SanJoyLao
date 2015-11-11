package rsantillanc.sanjoylao.storage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import rsantillanc.sanjoylao.model.FeastPlateModel;
import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;

/**
 * Created by RenzoD on 06/11/2015.
 */
public class OrderDao {

    private static final String SELECT = "Select * from " + Tables.ORDERS;
    private static final String COMPARE = "=?";
    private static final String SELECT_DETAILS = "Select * from " + Tables.ORDER_DETAIL;
    ;
    private final Context _context;

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

    //Columns detail
    private String objectIdDetail = "objectId";
    private String idFeastPlate = "idFeastPlate";
    private String idOrder = "idOrder";
    private String idPlateSize = "idPlateSize";
    private String counter = "counter";
    private String createdAtDetail = "createdAt";
    private String updatedAtDetail = "updatedAt";


    //Database
    private SQLiteDatabase db;

    public OrderDao(Context c) {
        SJLDatabase mSJLDatabase = new SJLDatabase(c);
        this.db = mSJLDatabase.getWritableDatabase();
        this._context = c;
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

        if (!cur.isClosed())
            cur.close();

        return order;
    }

    public OrderModel getOrderByID(String orderID) {
        Cursor cur = db.query(Tables.ORDERS, null, objectId + COMPARE, new String[]{orderID}, null, null, null);
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

    public long insertDetail(OrderDetailModel detail) {

        ContentValues cv = new ContentValues();
        cv.put(objectIdDetail, detail.getObjectId());
        cv.put(idPlateSize, detail.getPlateSize().getObjectId());
        cv.put(idOrder, detail.getOrder().getObjectId());
        cv.put(counter, detail.getCounter());
        cv.put(createdAtDetail, createdAt);
        cv.put(updatedAtDetail, createdAt);
        return db.insert(Tables.ORDER_DETAIL, null, cv);
    }

    private String makePlateSizeToJson(PlateSizeModel plateSize) {
        return new Gson().toJson(plateSize);
    }

    public List<OrderDetailModel> getOrderDetailsByOrderID(String orderID) {
        Cursor cur = db.query(Tables.ORDER_DETAIL, null, idOrder + COMPARE, new String[]{orderID}, null, null, null);
        return loopDetails(cur, new ArrayList<OrderDetailModel>());
    }

    private List<OrderDetailModel> loopDetails(Cursor cur, List<OrderDetailModel> listDetails) {

        if (cur.moveToFirst())
            do {

                OrderDetailModel detail = new OrderDetailModel();
                detail.setObjectId(cur.getString(cur.getColumnIndex(objectIdDetail)));
                detail.setOrder(makeOrder(cur.getString(cur.getColumnIndex(idOrder))));
                detail.setPlateSize(makePlateSize(cur.getString(cur.getColumnIndex(idPlateSize))));
                detail.setCounter(cur.getInt(cur.getColumnIndex(counter)));
                detail.setCreatedAt(cur.getString(cur.getColumnIndex(createdAtDetail)));
                //Add if exist
                if (cur.getString(cur.getColumnIndex(idFeastPlate)) != null)
                    detail.setFeastPlate(makeFeastPlate(cur.getString(cur.getColumnIndex(idFeastPlate))));

                detail.setUpdatedAt(cur.getString(cur.getColumnIndex(updatedAtDetail)));
                listDetails.add(detail);
            } while (cur.moveToNext());

        if (!cur.isClosed()) {
            cur.close();
        }

        return listDetails;
    }

    private FeastPlateModel makeFeastPlate(String feastPlateID) {
        return null;
    }

    private PlateSizeModel makePlateSize(String plateSizeID) {
        return new PlateSizeDao(_context).getPlateSize(plateSizeID);
    }

    private OrderModel makeOrder(String orderID) {
        return getOrderByID(orderID);
    }

    public int countDetails() {
        Cursor cur = db.rawQuery(SELECT_DETAILS, null);
        return cur.getCount();
    }

    public int deleteDetail(String objectId) {
        return db.delete(Tables.ORDER_DETAIL, objectIdDetail + COMPARE, new String[]{objectId});
    }
}
