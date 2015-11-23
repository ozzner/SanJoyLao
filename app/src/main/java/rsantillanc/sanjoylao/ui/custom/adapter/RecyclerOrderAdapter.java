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
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.ui.custom.view.CircleView;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLDates;
import rsantillanc.sanjoylao.util.SJLStrings;

/**
 * Created by RenzoD on 03/06/2015.
 */
public class RecyclerOrderAdapter extends RecyclerView.Adapter<RecyclerOrderAdapter.OrderViewHolder> {

    private static final int MAX_UNID = 100;
    private static final int MIN_UNID = 1;
    private boolean isHistory = false;
    private LayoutInflater layIn;
    private List<OrderDetailModel> details = Collections.EMPTY_LIST;
    private List<OrderModel> orders = Collections.EMPTY_LIST;
    private Context ctx;
    private OnOrderItemClickListener mItemClickListener;
    private double total = 0.0;


    public RecyclerOrderAdapter(List<OrderDetailModel> details, Context ctx) {
        this.layIn = LayoutInflater.from(ctx);
        this.details = details;
        this.ctx = ctx;
    }

    public RecyclerOrderAdapter(List<OrderModel> order, Context ctx, boolean isHistory) {
        this.layIn = LayoutInflater.from(ctx);
        this.orders = order;
        this.ctx = ctx;
        this.isHistory = isHistory;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHistory)
            return new OrderViewHolder(layIn.inflate(R.layout.item_order_history, parent, false));
        else
            return new OrderViewHolder(layIn.inflate(R.layout.item_orders, parent, false));

    }

    @Override
    public void onBindViewHolder(OrderViewHolder orderHolder, int index) {

        /**
         * Falta validar si el plato pertenece a un banquete,
         * de ser así se tiene que agregar una validación
         */

        if (!isHistory) {
            OrderDetailModel item = details.get(index);

            orderHolder.tvPrice.setText(Const.PRICE_PEN + SJLStrings.format((item.getPlateSize().getPrice() * item.getCounter()), SJLStrings.FORMAT_MILES_EN));
            orderHolder.tvPrice.setTypeface(SJLStrings.getSJLFont(ctx));
            orderHolder.tvName.setText(item.getPlateSize().getPlate().getName()); //Delete hardcode

            if (item.getCounter() == 1)
                orderHolder.tvCount.setVisibility(View.INVISIBLE);
            else
                orderHolder.tvCount.setVisibility(View.VISIBLE);

            orderHolder.tvCount.setText(Const.TAG_POR + item.getCounter());
            orderHolder.tvOptions.setText(item.getPlateSize().getSize().getName());

        } else {

            OrderModel order = orders.get(index);
            orderHolder.tvName.setText("Order: " + order.getObjectId());
            orderHolder.tvPrice.setText(Const.PRICE_PEN + SJLStrings.format((order.getPrice()), SJLStrings.FORMAT_MILES_EN));
            orderHolder.tvDatatime.setText(SJLDates.customDateConverter(order.getCreatedAt(), SJLStrings.PARSE_DATE_FORMAT, SJLDates.FORMAT_DATE_2));
            switch (order.getStatus().getCode()) {

                     /*Cuando se agrega el primer plato de una orden.*/
                case Const.STATUS_TEMPORAL:
                    orderHolder.cvStatus.setStrokeColor(ctx.getResources().getColor(R.color.blue));
                    orderHolder.cvStatus.setFillColor(ctx.getResources().getColor(R.color.blue));
                    orderHolder.tvStatus.setText("Status: " + order.getStatus().getName());
                    break;

                     /*Cuando se cancela una orden por cliente.*/
                case Const.STATUS_CANCELLED:
                    orderHolder.cvStatus.setStrokeColor(ctx.getResources().getColor(R.color.red));
                    orderHolder.cvStatus.setFillColor(ctx.getResources().getColor(R.color.red));
                    orderHolder.tvStatus.setText("Status: " + order.getStatus().getName());
                    break;

                    /*Cuando se determina el tiempo de entrega de la orden o de recojo.*/
                case Const.STATUS_CONFIRMED:
                    orderHolder.cvStatus.setStrokeColor(ctx.getResources().getColor(R.color.my_green));
                    orderHolder.cvStatus.setFillColor(ctx.getResources().getColor(R.color.my_green));
                    orderHolder.tvStatus.setText("Status: " + order.getStatus().getName());
                    break;

                    /*Cuando se entregó la orden de manera satisfactoria.*/
                case Const.STATUS_DELIVERED:
                    orderHolder.cvStatus.setStrokeColor(ctx.getResources().getColor(R.color.yellow_600));
                    orderHolder.cvStatus.setFillColor(ctx.getResources().getColor(R.color.yellow_600));
                    orderHolder.tvStatus.setText("Status: " + order.getStatus().getName());
                    break;

                    /*Cuando la orden esta en camino (Solo Delivery).*/
                case Const.STATUS_ON_ROAD:
                    orderHolder.cvStatus.setStrokeColor(ctx.getResources().getColor(R.color.orange_800));
                    orderHolder.cvStatus.setFillColor(ctx.getResources().getColor(R.color.orange_800));
                    orderHolder.tvStatus.setText("Status: " + order.getStatus().getName());
                    break;

                    /*Cuando se culmina la orden, indica el tipo de pedido y la forma de pago.*/
                case Const.STATUS_RECEIVED:
                    orderHolder.cvStatus.setStrokeColor(ctx.getResources().getColor(R.color.gray));
                    orderHolder.cvStatus.setFillColor(ctx.getResources().getColor(R.color.gray));
                    orderHolder.tvStatus.setText("Status: " + order.getStatus().getName());
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        if (!isHistory)
            return details.size();
        else
            return orders.size();

    }

    public List<OrderDetailModel> getDetails() {
        return details;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Details
        private ImageView ivCountAdd;
        private ImageView ivCountDel;
        private ImageView ivIngredients;
        private ImageView ivOpenImage;
        private ImageView ivDelete;
        private TextView tvName;
        private TextView tvOptions;
        private TextView tvPrice;
        private TextView tvCount;
        //history
        private TextView tvStatus;
        private TextView tvDatatime;
        private CircleView cvStatus;

        public OrderViewHolder(View vi) {
            super(vi);

            if (!isHistory) {

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

            } else {
                tvPrice = (TextView) vi.findViewById(R.id.tv_history_price);
                tvDatatime = (TextView) vi.findViewById(R.id.tv_history_datetime);
                tvName = (TextView) vi.findViewById(R.id.tv_order_history_id);
                tvStatus = (TextView) vi.findViewById(R.id.tv_history_status_name);
                cvStatus = (CircleView) vi.findViewById(R.id.circle_view);
            }

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null)
                if (!isHistory) {

                    if (v == ivCountAdd)
                        incrementedCounter(getPosition());
                    else if (v == ivCountDel)
                        decrementCounter(getPosition());
                    else if (v == ivIngredients)
                        mItemClickListener.onOpenIngredients(details.get(getPosition()).getPlateSize().getPlate());
                    else if (v == ivOpenImage)
                        mItemClickListener.onOpenImage(details.get(getPosition()).getPlateSize());
                    else if (v == ivDelete)
                        deleteItemDetail(getPosition(), details.get(getPosition()));
//                else
//                    mItemClickListener.onItemClick(v, getPosition());

                } else {

                }


        }
    }

    private void deleteItemDetail(int position, OrderDetailModel orderDetail) {
        details.remove(position);
        mItemClickListener.onDeleteItem(orderDetail);
    }

    private void decrementCounter(int position) {
        int counter = details.get(position).getCounter();
        if (counter > MIN_UNID)
            counter--;

        details.get(position).setCounter(counter);
        mItemClickListener.onDecrementCount();
    }

    private void incrementedCounter(int position) {
        int counter = details.get(position).getCounter();
        if (counter < MAX_UNID)
            counter++;

        details.get(position).setCounter(counter);
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
