package rsantillanc.sanjoylao.ui.mvp.Main;

import android.support.v4.app.Fragment;

/**
 * Created by rsantillanc on 20/10/2015.
 */
public interface IMainView {
    void updateTitle(CharSequence title);
    void closeMenu();
    void openMenu();
    void displayFragment(Fragment ui, boolean isSecondary);
    void markItemSelected(int item);
    void collapse(boolean b);
}
