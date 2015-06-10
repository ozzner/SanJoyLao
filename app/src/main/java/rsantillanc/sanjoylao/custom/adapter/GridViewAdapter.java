package rsantillanc.sanjoylao.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.OptionsModel;

/**
 * Created by RenzoD on 09/06/2015.
 */
public class GridViewAdapter extends BaseAdapter implements View.OnClickListener{

    private OptionsModel oOption;
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
        View vi = convertView;
        OptionsGridViewHolder holder;
        oOption = (OptionsModel)getItem(position);

        if (vi == null){
            holder = new OptionsGridViewHolder();

            vi =  layIn.inflate(R.layout.row_grid_options,parent,false);
            holder.ivLoader = (ImageView)vi.findViewById(R.id.iv_loader);
            vi.setTag(holder);
        }else{
            holder = (OptionsGridViewHolder)vi.getTag();
        }

        /*SetUp*/
        holder.ivLoader.setImageResource(R.drawable.plate_3);

        return vi;
    }

    @Override
    public void onClick(View v) {
        //TODO open details activity.
    }


    static class OptionsGridViewHolder{
        ImageView ivLoader;
    }
}
