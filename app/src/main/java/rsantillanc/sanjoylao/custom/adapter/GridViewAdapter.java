package rsantillanc.sanjoylao.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private int imgWidth;
    private OnPlateClickListener mListener;



    public GridViewAdapter(Context ctx, List<OptionsModel> optionsItems,int width) {
        this.ctx = ctx;
        this.optionsItems = optionsItems;
        this.layIn = LayoutInflater.from(ctx);
        this.imgWidth = width;
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
            holder.tvName = (TextView) vi.findViewById(R.id.tv_name_plate_grid_options);
            vi.setTag(holder);

        }else{
            holder = (OptionsGridViewHolder)vi.getTag();
        }

        /*SetUp*/
        holder.ivLoader.setLayoutParams(new RelativeLayout.LayoutParams(imgWidth, imgWidth));
        holder.ivLoader.setImageResource(R.drawable.plate_1);
        holder.ivLoader.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if (position%2 == 0)
        holder.tvName.setText(oOption.getPlateName1());
        else
        holder.tvName.setText(oOption.getPlateName2());

        return vi;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.bt_options_grid_order)
            mListener.onClicked(v);
    }

    public void setOnPlateClickListener(OnPlateClickListener listener){
        this.mListener = listener;
    }

    public interface OnPlateClickListener{
        void onClicked(View v);
    }
    static class OptionsGridViewHolder{
        ImageView ivLoader;
        Button btOrder;
        TextView tvName;
    }

}
