package rsantillanc.sanjoylao.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by RenzoD on 04/06/2015.
 */
public class OptionsModel implements Serializable {

    private int imgPlateOne;
    private int imgPlateTwo;
    private String plateName1;
    private String plateName2;
    private String title;



    public OptionsModel(int imgPlateOne, int imgPlateTwo, String plateName1, String plateName2,String title) {
        this.imgPlateOne = imgPlateOne;
        this.imgPlateTwo = imgPlateTwo;
        this.plateName1 = plateName1;
        this.plateName2 = plateName2;
        this.title = title;
    }

    public OptionsModel() {
    }

    public ArrayList<OptionsModel> testData(){

        ArrayList<OptionsModel> model = new ArrayList<>();
        model.add(new OptionsModel(0,0,"Tallarin Saltado","Chaufa de pollo","Primara opción"));
        model.add(new OptionsModel(0,0,"Chaufa tapado","Kam lu Wantan","Segunda opción"));
        model.add(new OptionsModel(0,0,"Cru Yoc","Chaufa de pollo","Tercera opción"));
        model.add(new OptionsModel(0,0,"Kayten","Cru-Kay","Cuarta opción"));
        model.add(new OptionsModel(0,0,"Polloo cinco sabores","Pollo con piña y durazno","Quinta opción"));
        model.add(new OptionsModel(0,0,"Chancho al Ajo","Pollo ti pa kay","Sexta opción"));

        return model;
    }

    public int getImgPlateOne() {
        return imgPlateOne;
    }

    public void setImgPlateOne(int imgPlateOne) {
        this.imgPlateOne = imgPlateOne;
    }

    public int getImgPlateTwo() {
        return imgPlateTwo;
    }

    public void setImgPlateTwo(int imgPlateTwo) {
        this.imgPlateTwo = imgPlateTwo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlateName1() {
        return plateName1;
    }

    public void setPlateName1(String plateName1) {
        this.plateName1 = plateName1;
    }

    public String getPlateName2() {
        return plateName2;
    }

    public void setPlateName2(String plateName2) {
        this.plateName2 = plateName2;
    }
}
