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

/**
 * Created by RenzoD on 03/06/2015.
 */
public class RecyclerOrderAdapter extends RecyclerView.Adapter<RecyclerOrderAdapter.BanquetViewHolder> {

    private LayoutInflater layIn;
    private List<Object> banquetItems = Collections.EMPTY_LIST;
    private Context ctx;
    private OnOrderItemClickListener mItemClickListener;


    public RecyclerOrderAdapter(List<Object> banquetItems, Context ctx) {
        this.layIn = LayoutInflater.from(ctx);
        this.banquetItems = banquetItems;
        this.ctx = ctx;
    }

    @Override
    public BanquetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BanquetViewHolder(layIn.inflate(R.layout.item_orders, parent, false));
    }

    @Override
    public void onBindViewHolder(BanquetViewHolder orderHolder, int index) {
//        FeastModel banquet = (FeastModel) banquetItems.get(index);
//
//        orderHolder.tvPrice.setText(Const.PRICE_PEN + SJLStrings.format(banquet.getPrice(), SJLStrings.FORMAT_MILES_EN));
//        orderHolder.tvPrice.setTypeface(SJLStrings.getSJLFont(ctx));
//        orderHolder.tvName.setText(banquet.getName()); //Delete hardcode
//        orderHolder.tvCount.setText(Const.TAG_POR + (index + 1));
//
//        if (banquet.isFlagOptions())
//            orderHolder.tvOptions.setText("Las " + banquet.getOption() + " que eligió");//Delete hardcode
//        else
//            orderHolder.tvOptions.setText("La opción elegida.");//Delete hardcode


    }

    @Override
    public int getItemCount() {
        return banquetItems.size();
    }


    class BanquetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView ivCountAdd;
        private final ImageView ivCountDel;
        private final ImageView ivIngredients;
        private final ImageView ivOpenImage;
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
            tvCount = (TextView) vi.findViewById(R.id.tv_order_count_item);
            ivCountAdd = (ImageView) vi.findViewById(R.id.iv_order_count_add);
            ivCountDel = (ImageView) vi.findViewById(R.id.iv_order_count_delete);
            ivIngredients = (ImageView) vi.findViewById(R.id.iv_order_open_ingredients);
            ivOpenImage = (ImageView) vi.findViewById(R.id.iv_order_open_image);
            ivDelete = (ImageView) vi.findViewById(R.id.iv_order_delete);

            vi.setOnClickListener(this);
            ivCountAdd.setOnClickListener(this);
            ivCountDel.setOnClickListener(this);
            ivIngredients.setOnClickListener(this);
            ivOpenImage.setOnClickListener(this);
            ivDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null)
                if (v == ivCountAdd)
                    mItemClickListener.onAddCount();
                else if (v == ivCountDel)
                    mItemClickListener.onRemoveCount();
                else if (v == ivIngredients)
                    mItemClickListener.onOpenIngredients();
                else if (v == ivOpenImage)
                    mItemClickListener.onOpenImage();
                else if (v == ivDelete)
                    mItemClickListener.onDeleteItem();
                else
                    mItemClickListener.onItemClick(v, getPosition());


        }
    }


    public void setOnItemClickListener(OnOrderItemClickListener mListener) {
        this.mItemClickListener = mListener;
    }

    public interface OnOrderItemClickListener {
        void onItemClick(View v, int index);

        void onOpenImage();

        void onOpenIngredients();

        void onAddCount();

        void onRemoveCount();

        void onDeleteItem();
    }

}
