package rsantillanc.sanjoylao.model;

import java.util.List;

/**
 * Created by RenzoD on 08/11/2015.
 */
public class RelationPlateSize {

    private PlateModel currentPlate;
    private List<PlateSizeModel> listSizes;


    public RelationPlateSize(List<PlateSizeModel> listSizes, PlateModel currentPlate) {
        this.listSizes = listSizes;
        this.currentPlate = currentPlate;
    }

    public RelationPlateSize() {

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
