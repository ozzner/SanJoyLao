package rsantillanc.sanjoylao.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

import rsantillanc.sanjoylao.model.OptionsModel;

/**
 * Created by RenzoD on 09/06/2015.
 */
public class GridViewAdapter extends BaseAdapter {

    private LayoutInflater layIn;
    private List<OptionsModel> optionsItems = Collections.EMPTY_LIST;
    private Context ctx;

//    OnItemClickListener mItemClickListener;


    public GridViewAdapter(Context ctx, List<OptionsModel> optionsItems) {
        this.ctx = ctx;
        this.optionsItems = optionsItems;
        this.layIn = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return optionsItems.size();
    }

    @Override
    public Object getItem(int position)
    {
        OptionsModel model = optionsItems.get(position);
        return model;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    static class OptionsGridViewHolder{

    }
}
