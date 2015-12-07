package rsantillanc.sanjoylao.model;

import java.util.ArrayList;

/**
 * Created by RenzoD on 06/12/2015.
 */
public class ParseOpponentsRelation {

    public ParseOpponentsRelation() {
        this.__op = "AddRelation";
        this.objects = new ArrayList<>();
    }

    private String __op;

    private ArrayList<ParsePointerModel> objects;

    public ArrayList<ParsePointerModel> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<ParsePointerModel> objects) {
        this.objects = objects;
    }
}
