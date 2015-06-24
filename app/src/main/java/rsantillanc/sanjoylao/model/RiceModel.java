package rsantillanc.sanjoylao.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by RenzoD on 11/06/2015.
 */
public class RiceModel implements Serializable {

    private static final long serialVersionUID = 0L;

    private String title;
    private double priceTaza;
    private double priceFuente;
    private int image;


    public RiceModel(String title, double priceTaza, double priceFuente, int image) {
        this.title = title;
        this.priceTaza = priceTaza;
        this.priceFuente = priceFuente;
        this.image = image;
    }

    public RiceModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPriceFuente() {
        return priceFuente;
    }

    public void setPriceFuente(double priceFuente) {
        this.priceFuente = priceFuente;
    }

    public double getPriceTaza() {
        return priceTaza;
    }

    public void setPriceTaza(double priceTaza) {
        this.priceTaza = priceTaza;
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
        model.add(new RiceModel("Arroz Chaufa con Pollo",12.25,24.25,0));
        model.add(new RiceModel("Arroz Chaufa especial (Pollo, chanco y langostino)",15.10,30.10,0));

        return model;
    }




}
