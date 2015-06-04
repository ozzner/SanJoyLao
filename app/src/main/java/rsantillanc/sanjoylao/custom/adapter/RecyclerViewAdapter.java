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

/**
 * Created by RenzoD on 03/06/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BanquetViewHolder> {

    private LayoutInflater layIn;
    private List<BanquetModel> banquetItems = Collections.EMPTY_LIST;
    private Context ctx;


    public RecyclerViewAdapter(List<BanquetModel> banquetItems, Context ctx) {
        this.layIn = LayoutInflater.from(ctx);
        this.banquetItems = banquetItems;
        this.ctx = ctx;
    }

    @Override
    public BanquetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View banquet = layIn.inflate(R.layout.row_item_banquet,parent,false);
        return new BanquetViewHolder(banquet);
    }

    @Override
    public void onBindViewHolder(BanquetViewHolder holder, int index) {
       BanquetModel banquet = banquetItems.get(index);
       holder.tvPrice.setText(String.valueOf(banquet.getPrice()));
       holder.tvName.setText(banquet.getName());
       holder.tvOptions.setText(banquet.getOption());
    }

    @Override
    public int getItemCount() {
        return banquetItems.size();
    }


    class BanquetViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvOptions;
        TextView tvPrice;
        ImageView ivBanquet;

        public BanquetViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_banquet_name);
            tvOptions = (TextView) itemView.findViewById(R.id.tv_banquet_options);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_banquet_price);
            ivBanquet = (ImageView) itemView.findViewById(R.id.iv_banquet);
        }
    }




}
