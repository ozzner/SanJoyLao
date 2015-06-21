package rsantillanc.sanjoylao.custom.adapter;

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
import rsantillanc.sanjoylao.model.BanquetModel;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 03/06/2015.
 */
public class RecyclerViewBanquetAdapter extends RecyclerView.Adapter<RecyclerViewBanquetAdapter.BanquetViewHolder> {

    private LayoutInflater layIn;
    private List<Object> banquetItems = Collections.EMPTY_LIST;
    private Context ctx;
    private OnItemClickListener mItemClickListener;


    public RecyclerViewBanquetAdapter(List<Object> banquetItems, Context ctx) {
        this.layIn = LayoutInflater.from(ctx);
        this.banquetItems = banquetItems;
        this.ctx = ctx;
    }

    @Override
    public BanquetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View banquet = layIn.inflate(R.layout.row_banquet,parent,false);
        return new BanquetViewHolder(banquet);
    }

    @Override
    public void onBindViewHolder(BanquetViewHolder holder, int index) {
       BanquetModel banquet = (BanquetModel) banquetItems.get(index);
       holder.tvPrice.setText(Const.PRICE_PEN + String.valueOf(banquet.getPrice()));
       holder.tvName.setText(banquet.getName());
       holder.tvOptions.setText(banquet.getOption());
    }

    @Override
    public int getItemCount() {
        return banquetItems.size();
    }


    class BanquetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName;
        TextView tvOptions;
        TextView tvPrice;
        ImageView ivBanquet;

        public BanquetViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_banquet_name);
            tvOptions = (TextView) itemView.findViewById(R.id.tv_banquet_option);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_banquet_price);
            ivBanquet = (ImageView) itemView.findViewById(R.id.iv_banquet);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(v,getPosition());
        }
    }


    public void setOnItemClickListener(OnItemClickListener mListener){
        this.mItemClickListener = mListener;
    }

    public interface OnItemClickListener{
       void onItemClick(View v , int index);
    }




}
