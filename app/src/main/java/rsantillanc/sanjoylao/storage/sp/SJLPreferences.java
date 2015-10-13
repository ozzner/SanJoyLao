package rsantillanc.sanjoylao.storage.sp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by RenzoD on 20/06/2015.
 */
public class SJLPreferences {

    //To config.
    private static final int MODE_PRIVATE = Context.MODE_PRIVATE;
    private static final String SJL_PREFERENCES = "ItyPreferences";

    public static final int INT_DEFAULT_VALUE = -19;//My birthday negative :D on July
    //Keys
    public  static final String KEY_ORDER_TYPE = "sjl_my_order";
    //Global vars
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;
    private Context _context;


    /**
     * @see <p>Método que inicia las preferencias.</p>
     * @param _context Contexto de la aplicación.
     */
    public SJLPreferences(Context _context) {
        this._context = _context;
        mPreferences = _context.getSharedPreferences(SJL_PREFERENCES,MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public int saveOrderType(int type){
        try {
            mEditor.putInt(KEY_ORDER_TYPE,type);
            mEditor.commit();
            return 1;
        }catch (Exception ex){
            return 0;
        }
    }

    public int getSaveStoredByKey(String key){
        return   mPreferences.getInt(key, INT_DEFAULT_VALUE);
    }

    public void run(){
        try {
            mEditor.putInt(KEY_ORDER_TYPE,INT_DEFAULT_VALUE);
            mEditor.commit();
        }catch (Exception ex){
        }
    }

}
