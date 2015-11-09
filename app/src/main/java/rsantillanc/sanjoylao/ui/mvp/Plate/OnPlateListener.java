package rsantillanc.sanjoylao.ui.mvp.Plate;

import java.util.List;

import rsantillanc.sanjoylao.model.RelationPlateSizeModel;

/**
 * Created by rsantillanc on 04/11/2015.
 */
public interface OnPlateListener {
    void onListFilterSuccess(List<RelationPlateSizeModel> platesFilter);
    void onListFilterError(CharSequence error);
    void onPlateAddSucess(int size);

}
