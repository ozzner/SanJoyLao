package rsantillanc.sanjoylao.ui.custom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLStrings;

/**
 * Created by RenzoD on 03/06/2015.
 */
public class RecyclerPlateAdapter extends RecyclerView.Adapter<RecyclerPlateAdapter.PlateHolder> {

    private LayoutInflater layIn;
    private List<PlateModel> plates = Collections.EMPTY_LIST;
    private OnItemPlateClickListener mItemClickListener;
    private Context _context;


    public RecyclerPlateAdapter(List<PlateModel> items, Context ctx) {
        this.layIn = LayoutInflater.from(ctx);
        this.plates = items;
        this._context = ctx;
    }

    @Override
    public PlateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ui = layIn.inflate(R.layout.item_plate, parent, false);
        return new PlateHolder(ui);
    }

    @Override
    public void onBindViewHolder(PlateHolder holder, int index) {
        PlateModel plate = plates.get(index);
        TextView[] names = {holder.tvName1, holder.tvName2, holder.tvName3};
        TextView[] prices = {holder.tvPrice1, holder.tvPrice2, holder.tvPrice3};

        holder.tvPlateName.setText(plate.getName());
        for (int i = 0; i < plate.getPlateSize().size(); i++) {
            printValues(names[i], prices[i], plate.getPlateSize().get(i));
        }

    }

    private void printValues(TextView name, TextView price, PlateSizeModel plateSize) {

        //Enabled visibility
        name.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);

        //Set values
        name.setText(plateSize.getSize().getName());
        price.setText(Const.PRICE_PEN +SJLStrings.format(plateSize.getPrice(), SJLStrings.FORMAT_MILES_EN));

    }

    @Override
    public int getItemCount() {
        return plates.size();
    }


    class PlateHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvPlateName;
        TextView tvPrice1;
        TextView tvPrice2;
        TextView tvPrice3;
        TextView tvName1;
        TextView tvName2;
        TextView tvName3;
        ImageView ivAddPlate;

        public PlateHolder(View itemView) {
            super(itemView);
            tvPlateName = (TextView) itemView.findViewById(R.id.tv_plate_name);
            tvPrice1 = (TextView) itemView.findViewById(R.id.tv_plate_price1);
            tvPrice2 = (TextView) itemView.findViewById(R.id.tv_plate_price2);
            tvPrice3 = (TextView) itemView.findViewById(R.id.tv_plate_price3);
            tvName1 = (TextView) itemView.findViewById(R.id.tv_plate_size_name1);
            tvName2 = (TextView) itemView.findViewById(R.id.tv_plate_size_name2);
            tvName3 = (TextView) itemView.findViewById(R.id.tv_plate_size_name3);
            ivAddPlate = (ImageView) itemView.findViewById(R.id.iv_plate_add);

            //Click
            ivAddPlate.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {

                if (v instanceof ImageView)
                    mItemClickListener.onAddPlateClick(v);
                else
                    mItemClickListener.onItemClick(plates.get(getPosition()));

            }
        }
    }


    public void setOnItemPlateClickListener(OnItemPlateClickListener mListener) {
        this.mItemClickListener = mListener;
    }

    public interface OnItemPlateClickListener {
        void onItemClick(PlateModel plate);

        void onAddPlateClick(View v);
    }


}
