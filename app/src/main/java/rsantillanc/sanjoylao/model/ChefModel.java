package rsantillanc.sanjoylao.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ChefModel implements Serializable {

    private static final long serialVersionUID = 0L;

    private String name;
    private double price;
    private int image;


    public ChefModel(String name, double price, int image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public ChefModel() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
        model.add(new ChefModel("Pescado \"San Joy Lao\"",70.00,-1));
        model.add(new ChefModel("Chi Jau Cuy",90.00,-1));
        model.add(new ChefModel("Ap Sum",80.00,-1));

        return model;
    }




}
