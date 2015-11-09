package rsantillanc.sanjoylao.model;

import java.util.List;

/**
 * Created by RenzoD on 08/11/2015.
 */
public class RelationPlateSizeModel {

    private PlateModel currentPlate;
    private List<PlateSizeModel> listSizes;


    public RelationPlateSizeModel(List<PlateSizeModel> listSizes, PlateModel currentPlate) {
        this.listSizes = listSizes;
        this.currentPlate = currentPlate;
    }

    public RelationPlateSizeModel() {

    }

    public PlateModel getCurrentPlate() {
        return currentPlate;
    }

    public void setCurrentPlate(PlateModel currentPlate) {
        this.currentPlate = currentPlate;
    }

    public List<PlateSizeModel> getListSizes() {
        return listSizes;
    }

    public void setListSizes(List<PlateSizeModel> listSizes) {
        this.listSizes = listSizes;
    }
}
