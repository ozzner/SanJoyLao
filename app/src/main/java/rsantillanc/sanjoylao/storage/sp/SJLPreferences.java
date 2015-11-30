package rsantillanc.sanjoylao.storage.sp;

import android.content.Context;
import android.content.SharedPreferences;

import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 20/06/2015.
 */
public class SJLPreferences {

    //To config.
    private static final int MODE_PRIVATE = Context.MODE_PRIVATE;
    private static final String SJL_PREFERENCES = "ItyPreferences";

    public static final int INT_DEFAULT_VALUE = -19;//My birthday negative :D on July
    public static final boolean INSTALLATION = true;


    //Global vars
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;
    private Context _context;


    /**
     * @param _context Contexto de la aplicación.
     * @see <p>Método que inicia las preferencias.</p>
     */
    public SJLPreferences(Context _context) {
        this._context = _context;
        mPreferences = _context.getSharedPreferences(SJL_PREFERENCES, MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public int saveOrderType(int type) {
        try {
            mEditor.putInt(Const.KEY_ORDER_TYPE, type);
            mEditor.commit();
            return 1;
        } catch (Exception ex) {
            return 0;
        }
    }

    public int getSaveStoredByKey(String key) {
        return mPreferences.getInt(key, INT_DEFAULT_VALUE);
    }

    public void run() {
        try {
            mEditor.putInt(Const.KEY_ORDER_TYPE, INT_DEFAULT_VALUE);
            mEditor.commit();
        } catch (Exception ex) {
        }
    }

    /**
     * Set Installation
     */
    public void install() {
        mEditor.putBoolean(Const.KEY_INSTALLATION, INSTALLATION);
        mEditor.commit();
    }

    /**
     * Get installation value.
     *
     * @return boolean value.
     */
    public boolean isInstallation() {
        return mPreferences.getBoolean(Const.KEY_INSTALLATION, true);
    }

    public void saveCurrentAmount(double amount) {
        mEditor.putLong(Const.KEY_CURRENT_AMOUNT, ((long) amount));
        mEditor.commit();
    }

    public long getCurrentAmount() {
        return mPreferences.getLong(Const.KEY_CURRENT_AMOUNT, 0);
    }

    public void saveCounter(int size) {
        mEditor.putInt(Const.KEY_COUNTER,size);
        mEditor.commit();
    }

    public int getCounter(){
        return mPreferences.getInt(Const.KEY_COUNTER, 0);
    }
}
