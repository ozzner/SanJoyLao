package rsantillanc.sanjoylao.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by RenzoD on 11/06/2015.
 */
public class SoupModel implements Serializable {

    private static final long serialVersionUID = 0L;

    private String title;
    private double pricePersonal;
    private double priceMedium;
    private double priceBig;
    private int image;


    public SoupModel(String title, double pricePersonal, double priceMedium, double priceBig, int image) {
        this.title = title;
        this.pricePersonal = pricePersonal;
        this.priceMedium = priceMedium;
        this.priceBig = priceBig;
        this.image = image;
    }

    public SoupModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPricePersonal() {
        return pricePersonal;
    }

    public void setPricePersonal(double pricePersonal) {
        this.pricePersonal = pricePersonal;
    }

    public double getPriceMedium() {
        return priceMedium;
    }

    public void setPriceMedium(double priceMedium) {
        this.priceMedium = priceMedium;
    }

    public double getPriceBig() {
        return priceBig;
    }

    public void setPriceBig(double priceBig) {
        this.priceBig = priceBig;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    //******************************** hardcode

    public ArrayList<Object> testData(){

        ArrayList<Object> model = new ArrayList<>();
        model.add(new SoupModel("Sopa wantan",12,15,18,-1));
        model.add(new SoupModel("Sopa Sam Sí",6.00,12.00,18.88,-1));
//        model.add(new SoupModel("Sopa Wo Min",8,15,21,-1));
//        model.add(new SoupModel("Caldo de Pichón",9,18,25,-1));
//        model.add(new SoupModel("CSopa Siu Kao \"San Joy Lao\"",12,20,30,-1));

        return model;
    }




}
