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
import rsantillanc.sanjoylao.model.BanquetModel;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 03/06/2015.
 */
public class RecyclerViewOrderAdapter extends RecyclerView.Adapter<RecyclerViewOrderAdapter.BanquetViewHolder> {

    private LayoutInflater layIn;
    private List<Object> banquetItems = Collections.EMPTY_LIST;
    private Context ctx;
    private OnItemClickListener mItemClickListener;


    public RecyclerViewOrderAdapter(List<Object> banquetItems, Context ctx) {
        this.layIn = LayoutInflater.from(ctx);
        this.banquetItems = banquetItems;
        this.ctx = ctx;
    }

    @Override
    public BanquetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BanquetViewHolder(layIn.inflate(R.layout.row_orders, parent, false));
    }

    @Override
    public void onBindViewHolder(BanquetViewHolder orderHolder, int index) {
        BanquetModel banquet = (BanquetModel) banquetItems.get(index);

        orderHolder.tvPrice.setText(Const.PRICE_PEN + String.valueOf(banquet.getPrice()));
        orderHolder.tvName.setText((index + 1) + ".- " + banquet.getName());//Delete hardcode
        orderHolder.tvCount.setText(Const.TAG_POR + (index + 1) );

        if (banquet.isFlagOptions())
            orderHolder.tvOptions.setText("Las " + banquet.getOption() + " que eligió");//Delete hardcode
        else
            orderHolder.tvOptions.setText("La opción elegida.");//Delete hardcode
    }

    @Override
    public int getItemCount() {
        return banquetItems.size();
    }


    class BanquetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView ivInfo;
        private final ImageView ivDelete;
        private final TextView tvName;
        private final TextView tvOptions;
        private final TextView tvPrice;
        private final TextView tvCount;

        public BanquetViewHolder(View vi) {
            super(vi);

            tvName = (TextView) vi.findViewById(R.id.tv_order_name);
            tvOptions = (TextView) vi.findViewById(R.id.tv_order_option);
            tvPrice = (TextView) vi.findViewById(R.id.tv_order_price);
            ivInfo = (ImageView) vi.findViewById(R.id.iv_order_info);
            ivDelete = (ImageView) vi.findViewById(R.id.iv_order_delete);
            tvCount = (TextView) vi.findViewById(R.id.tv_order_count_item);

            vi.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(v, getPosition());
        }
    }


    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mItemClickListener = mListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int index);
    }

}
