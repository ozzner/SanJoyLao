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
import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLStrings;

/**
 * Created by RenzoD on 03/06/2015.
 */
public class RecyclerOrderAdapter extends RecyclerView.Adapter<RecyclerOrderAdapter.BanquetViewHolder> {

    private static final int MAX_UNID = 100;
    private static final int MIN_UNID = 1;
    private LayoutInflater layIn;
    private List<OrderDetailModel> orders = Collections.EMPTY_LIST;
    private Context ctx;
    private OnOrderItemClickListener mItemClickListener;
    private double total = 0.0;


    public RecyclerOrderAdapter(List<OrderDetailModel> details, Context ctx) {
        this.layIn = LayoutInflater.from(ctx);
        this.orders = details;
        this.ctx = ctx;
    }

    @Override
    public BanquetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BanquetViewHolder(layIn.inflate(R.layout.item_orders, parent, false));
    }

    @Override
    public void onBindViewHolder(BanquetViewHolder orderHolder, int index) {
        OrderDetailModel item = orders.get(index);

        /**
         * Falta validar si el plato pertenece a un banquete,
         * de ser así se tiene que agregar una validación
         * Además falta aumentar el contador cuando han elegido el mismo plato.
         */

        orderHolder.tvPrice.setText(Const.PRICE_PEN + SJLStrings.format((item.getPlateSize().getPrice() * item.getCounter()), SJLStrings.FORMAT_MILES_EN));
        orderHolder.tvPrice.setTypeface(SJLStrings.getSJLFont(ctx));
        orderHolder.tvName.setText(item.getPlateSize().getPlate().getName()); //Delete hardcode

        if (item.getCounter() == 1)
            orderHolder.tvCount.setVisibility(View.INVISIBLE);
        else
            orderHolder.tvCount.setVisibility(View.VISIBLE);

        orderHolder.tvCount.setText(Const.TAG_POR + item.getCounter());
        orderHolder.tvOptions.setText(item.getPlateSize().getSize().getName());

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public List<OrderDetailModel> getOrders() {
        return orders;
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

//            vi.setOnClickListener(this);
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
                    incrementedCounter(getPosition());
                else if (v == ivCountDel)
                    decrementCounter(getPosition());
                else if (v == ivIngredients)
                    mItemClickListener.onOpenIngredients(orders.get(getPosition()).getPlateSize().getPlate());
                else if (v == ivOpenImage)
                    mItemClickListener.onOpenImage(orders.get(getPosition()).getPlateSize());
                else if (v == ivDelete)
                    deleteItemDetail(getPosition(),orders.get(getPosition()));
//                else
//                    mItemClickListener.onItemClick(v, getPosition());

        }
    }

    private void deleteItemDetail(int position, OrderDetailModel orderDetail) {
        orders.remove(position);
        mItemClickListener.onDeleteItem(orderDetail);
    }

    public double getTotalPrice() {
        for (OrderDetailModel order : orders) {
            total += (order.getPlateSize().getPrice() * order.getCounter());
        }
        return this.total;
    }

    private void decrementCounter(int position) {
        int counter = orders.get(position).getCounter();
        if (counter > MIN_UNID)
            counter--;

        orders.get(position).setCounter(counter);
        mItemClickListener.onDecrementCount();
    }

    private void incrementedCounter(int position) {
        int counter = orders.get(position).getCounter();
        if (counter < MAX_UNID)
            counter++;

        orders.get(position).setCounter(counter);
        mItemClickListener.onIncrementCount();
    }


    public void setOnItemClickListener(OnOrderItemClickListener mListener) {
        this.mItemClickListener = mListener;
    }

    public interface OnOrderItemClickListener {
        void onItemClick(View v, int index);

        void onOpenImage(PlateSizeModel plateSize);

        void onOpenIngredients(PlateModel plate);

        void onIncrementCount();

        void onDecrementCount();

        void onDeleteItem(OrderDetailModel orderDetailModel);
    }

}
