package rsantillanc.sanjoylao.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by RenzoD on 04/06/2015.
 */
public class CommentModel implements Serializable {

    private int userProfile;
    private String message;
    private String datatime;



    public CommentModel(int userProfile, String message, String datatime) {
        this.userProfile = userProfile;
        this.message = message;
        this.datatime = datatime;
    }

    public CommentModel() {
    }

    public ArrayList<Object> testData(){

        ArrayList<Object> model = new ArrayList<>();
        model.add(new CommentModel(0,"Muy bueno recomendable.","23:20"));
        model.add(new CommentModel(1,"Buen platillo, recomendable con yucas.","10:25"));
        model.add(new CommentModel(3,"Aceptable para la entrada","9:55"));
        model.add(new CommentModel(4,"Recontra buenazo, debería ser el plato de la casa.","Ayer"));
        model.add(new CommentModel(5,"Este platillo tiene que ser acompañado con pan al ajo.","3 dias"));
        model.add(new CommentModel(6,"Riquisimo","19-Jul"));

        return model;
    }

    public int getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(int userProfile) {
        this.userProfile = userProfile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }
}
