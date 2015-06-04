package rsantillanc.sanjoylao.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by RenzoD on 03/06/2015.
 */
public class BanquetModel implements Serializable {

    private double price;
    private String name;
    private String option;


    public BanquetModel(double price, String name, String option) {
        this.price = price;
        this.name = name;
        this.option = option;
    }

    public BanquetModel() {

    }

    public ArrayList<BanquetModel> testData(){

        ArrayList<BanquetModel> model = new ArrayList<>();
        model.add(new BanquetModel(55.65,"Banquete para 2 personas","6 opciones a elegir"));
        model.add( new BanquetModel(155.8,"Banquete para 6 personas","2 opciones a elegir"));
        model.add(new BanquetModel(95.88,"Banquete para 4 personas","4 opciones a elegir"));

        return model;
    }



    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
