package rsantillanc.sanjoylao.storage.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 21/10/2015.
 */
public class SJLDatabase extends SQLiteOpenHelper {

    //Debug
    private static final String TAG = SJLDatabase.class.getSimpleName();

    //Database
    private static final int VERSION = 12;
    private static final String DATABASE = "SJLDatabase.db";

    //Runtime
    private static final String[] TABLES = {
            Tables.USER,
            Tables.CATEGORY,
            Tables.SIZE,
            Tables.PLATE,
            Tables.PLATE_SIZE,
            Tables.ORDER_TYPE,
            Tables.ORDERS,
            Tables.ORDER_DETAIL,
            Tables.CALCULATION_TIME,
            Tables.FEAST,
            Tables.FEAST_PLATE,
            Tables.LOCAL_RESTAURANT,
            Tables.LOCATION_DELIVERY,
            Tables.PAYMENT,
            Tables.RESTAURANT,
            Tables.RUSH_HOUR,
            Tables.STATUS,
            Tables.SUGGESTION,
    };

    public SJLDatabase(Context context) {
        super(context, DATABASE, null, VERSION);
        installApp(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Tables.CREATE_USER);
        db.execSQL(Tables.CREATE_CATEGORY);
        db.execSQL(Tables.CREATE_PLATE);
        db.execSQL(Tables.CREATE_SIZE);
        db.execSQL(Tables.CREATE_PLATE_SIZE);
        db.execSQL(Tables.CREATE_ORDER_TYPE);
        db.execSQL(Tables.CREATE_ORDER);
        db.execSQL(Tables.CREATE_ORDER_DETAIL);
        db.execSQL(Tables.CREATE_CALCULATION_TIME);
        db.execSQL(Tables.CREATE_FEAST);
        db.execSQL(Tables.CREATE_FEAST_PLATE);
        db.execSQL(Tables.CREATE_LOCAL_RESTAURANT);
        db.execSQL(Tables.CREATE_LOCATION_DELIVERY);
        db.execSQL(Tables.CREATE_PAYMENT);
        db.execSQL(Tables.CREATE_RESTAURANT);
        db.execSQL(Tables.CREATE_RUSH_HOUR);
        db.execSQL(Tables.CREATE_STATUS);
        db.execSQL(Tables.CREATE_SUGGESTION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //loop
        for (int index = 0; index < TABLES.length; index++)
            dropTable(db, TABLES[index]);

        //Recreate
        onCreate(db);
    }

    private void installApp(Context c) {
//        SJLPreferences sp = new SJLPreferences(c);
//        sp.install();
    }

    private void dropTable(SQLiteDatabase db, String table) {
        try {
            db.execSQL(Tables.DROP_TABLE + table);
        } catch (Exception ex) {
            Log.e(Const.DEBUG, TAG + " Error dropping table, because " + table + " no exist.",ex);
        }
    }
}
