package rsantillanc.sanjoylao.model;

import java.io.Serializable;
import java.util.ArrayList;

import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 03/06/2015.
 */
public class BanquetModel implements Serializable {

    private static final long serialVersionUID = 0L;

    private double price;
    private String name;
    private String option;
    private boolean flagOptions;


    public BanquetModel(double price, String name, String option,boolean flag) {
        this.price = price;
        this.name = name;
        this.option = option;
        this.flagOptions = flag;
    }

    public BanquetModel() {
        this.name = Const.TAG_EMPTY;
        this.option = Const.TAG_EMPTY;
        this.price = 0.0;
        this.flagOptions = true;
    }

    public ArrayList<Object> testData(){

        ArrayList<Object> model = new ArrayList<>();
        model.add(new BanquetModel(55.65,"Banquete para 2 personas","6 opciones",true));
        model.add(new BanquetModel(152.00,"Banquete para 4 personas",Const.TAG_EMPTY,false));
        model.add( new BanquetModel(155.8,"Banquete para 6 personas","2 opciones",true));
        model.add(new BanquetModel(1500.00,"Banquete para 4 personas",Const.TAG_EMPTY,false));
        model.add(new BanquetModel(95.88,"Banquete para 4 personas","4 opciones",true));
        model.add(new BanquetModel(350.00,"Banquete para 4 personas",Const.TAG_EMPTY,false));
        model.add(new BanquetModel(95.88,"Banquete para 4 personas","4 opciones",true));

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

    public boolean isFlagOptions() {
        return flagOptions;
    }

    public void setFlagOptions(boolean flagOptions) {
        this.flagOptions = flagOptions;
    }
}
