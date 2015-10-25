package rsantillanc.sanjoylao.storage.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import rsantillanc.sanjoylao.storage.sp.SJLPreferences;

/**
 * Created by RenzoD on 21/10/2015.
 */
public class SJLDatabase extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABSE = "SJLDatabase.db";

    public SJLDatabase(Context context) {
        super(context, DATABSE, null, VERSION);
        installApp(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(Tables.CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(Tables.DROP_TABLE + Tables.USER);
    }

    private void installApp(Context c) {
        SJLPreferences sp = new SJLPreferences(c);
        sp.install();
    }
}
