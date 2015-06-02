package rsantillanc.sanjoylao.model;

/**
 * Created by RenzoD on 02/06/2015.
 */
public class NavigationDrawerModel {

    private String title;
    private int icon;

    public NavigationDrawerModel(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
