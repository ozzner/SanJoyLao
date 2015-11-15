package rsantillanc.sanjoylao.ui.custom.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.FeastModel;
import rsantillanc.sanjoylao.model.ChefModel;
import rsantillanc.sanjoylao.model.CommentModel;
import rsantillanc.sanjoylao.model.RiceModel;
import rsantillanc.sanjoylao.model.SoupModel;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLStrings;

/**
 * Created by RenzoD on 09/06/2015.
 */
public class ListViewAdapter extends BaseAdapter implements View.OnClickListener {

    //Comment
    private CommentModel oComment;
    //Soup
    private SoupModel oSoup;
    //Banquet
    private FeastModel oBanquet;
    //Rice
    private RiceModel oRice;
    //Chef
    private ChefModel oChef;

    private List<Object> listObjects;
    private Context _context;
    private LayoutInflater layIn;
    private int labelIndex;
    private OnItemClickListener mListener;


    public ListViewAdapter(Context _context, List<Object> list, int tag) {
        this.listObjects = list;
        this._context = _context;
        this.labelIndex = tag;
        this.layIn = LayoutInflater.from(_context);
    }


    @Override
    public int getCount() {
        return listObjects.size();
    }

    @Override
    public Object getItem(int position) {
        Object obj = listObjects.get(position);
        return obj;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;


        switch (labelIndex) {

            case Const.SOUP:
                SoupViewHolder soupHolder;
                oSoup = (SoupModel) getItem(position);

                if (vi == null) {
                    soupHolder = new SoupViewHolder();

                    vi = layIn.inflate(R.layout.row_soup, parent, false);
                    soupHolder.ivImageSoup = (ImageView) vi.findViewById(R.id.iv_image_soup);
                    soupHolder.ivInfo = (ImageView) vi.findViewById(R.id.iv_soup_info);
                    soupHolder.tvName = (TextView) vi.findViewById(R.id.tv_name);
                    soupHolder.tvTitle1 = (TextView) vi.findViewById(R.id.tv_title1);
                    soupHolder.tvTitle2 = (TextView) vi.findViewById(R.id.tv_title2);
                    soupHolder.tvTitle3 = (TextView) vi.findViewById(R.id.tv_title3);
                    soupHolder.tvPrice1 = (TextView) vi.findViewById(R.id.tv_price1);
                    soupHolder.tvPrice2 = (TextView) vi.findViewById(R.id.tv_price2);
                    soupHolder.tvPrice3 = (TextView) vi.findViewById(R.id.tv_price3);
                    soupHolder.btOrder = (Button) vi.findViewById(R.id.bt_order_soup);

                    vi.setTag(soupHolder);

                } else
                    soupHolder = (SoupViewHolder) vi.getTag();

                final String[] array = {
                        "Personal " + Const.PRICE_PEN + SJLStrings.format(oSoup.getPricePersonal(), SJLStrings.FORMAT_MILES_EN),
                        "Mediana " + Const.PRICE_PEN + SJLStrings.format(oSoup.getPriceMedium(), SJLStrings.FORMAT_MILES_EN),
                        "Grande " + Const.PRICE_PEN + SJLStrings.format(oSoup.getPriceBig(), SJLStrings.FORMAT_MILES_EN)};



                //Click listener info
                soupHolder.ivInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(v, position);
                    }
                });

                //Click open open alert
                soupHolder.btOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
                        builder.setTitle("Opciones")
                                .setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        Toast.makeText(_context, "Pedido correcto!", Toast.LENGTH_LONG).show();
                                    }
                                });

                        builder.create().show();
                    }
                });
                soupHolder.tvName.setText(oSoup.getTitle());
                soupHolder.tvPrice1.setText(Const.PRICE_PEN + SJLStrings.format(oSoup.getPricePersonal(), SJLStrings.FORMAT_MILES_EN));
                soupHolder.tvPrice2.setText(Const.PRICE_PEN + SJLStrings.format(oSoup.getPriceMedium(), SJLStrings.FORMAT_MILES_EN));
                soupHolder.tvPrice3.setText(Const.PRICE_PEN + SJLStrings.format(oSoup.getPriceBig(), SJLStrings.FORMAT_MILES_EN));

                if (position % 2 == 0)
                    soupHolder.ivImageSoup.setImageResource(R.drawable.plate_3);
                else
                    soupHolder.ivImageSoup.setImageResource(R.drawable.plate_2);

                vi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(v, position);
                    }
                });
                break;

            case Const.RICE:
                RiceViewHolder riceHolder;
                oRice = (RiceModel) getItem(position);

                if (vi == null) {
                    riceHolder = new RiceViewHolder();

                    vi = layIn.inflate(R.layout.row_rice, parent, false);
                    riceHolder.ivImageRice = (ImageView) vi.findViewById(R.id.iv_rice);
                    riceHolder.tvName = (TextView) vi.findViewById(R.id.tv_rice_name);
                    riceHolder.tvPrice1 = (TextView) vi.findViewById(R.id.tv_rice_price1);
                    riceHolder.tvPrice2 = (TextView) vi.findViewById(R.id.tv_rice_price2);
                    riceHolder.btOrder = (Button) vi.findViewById(R.id.bt_rice_order);

                    vi.setTag(riceHolder);

                } else
                    riceHolder = (RiceViewHolder) vi.getTag();

                riceHolder.tvName.setText(oRice.getTitle());
                riceHolder.tvPrice1.setText(Const.PRICE_PEN + SJLStrings.format(oRice.getPriceTaza(), SJLStrings.FORMAT_MILES_EN));
                riceHolder.tvPrice2.setText(Const.PRICE_PEN + SJLStrings.format(oRice.getPriceFuente(), SJLStrings.FORMAT_MILES_EN));


                if (position % 2 == 0)
                    riceHolder.ivImageRice.setImageResource(R.drawable.arroz_1);
                else
                    riceHolder.ivImageRice.setImageResource(R.drawable.arroz_2);

                riceHolder.ivImageRice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onItemClick(view,position);
                    }
                });

                vi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(v, position);
                    }
                });

                riceHolder.btOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onItemClick(view,position);
                    }
                });

                break;

            case Const.COMMENTS:

//                CommentViewHolder holder;
//                oComment = (CommentModel) getItem(position);
//
//                if (vi == null) {
//                    holder = new CommentViewHolder();
//
//
//                    vi = layIn.inflate(R.layout.item_comment, parent, false);
//                    holder.tvMessage = (TextView) vi.findViewById(R.id.tv_comment);
//                    holder.tvDatatime = (TextView) vi.findViewById(R.id.tv_comment_time);
//                    holder.ivUserProfile = (ImageView) vi.findViewById(R.id.iv_comment_profile);
//
//                    vi.setTag(holder);
//                } else
//                    holder = (CommentViewHolder) vi.getTag();
//
//
//                holder.tvMessage.setText(oComment.getMessage());
//                holder.tvDatatime.setText(oComment.getDatatime());
//                break;
//
//            case Const.ORDERS:
//                //TODO borrar luego ya que no es correcto, solo es por pruebas.
//                OrdersViewHolder orderHolder = null;
//                oBanquet = (FeastModel) getItem(position);
//
//                if (vi == null) {
//                    vi = layIn.inflate(R.layout.item_orders, parent, false);
//
//                    orderHolder = new OrdersViewHolder();
//                    orderHolder.tvName = (TextView) vi.findViewById(R.id.tv_order_name);
//                    orderHolder.tvOptions = (TextView) vi.findViewById(R.id.tv_order_option);
//                    orderHolder.tvPrice = (TextView) vi.findViewById(R.id.tv_order_price);
//                    orderHolder.ivInfo = (ImageView) vi.findViewById(R.id.iv_order_info);
//                    orderHolder.ivDelete = (ImageView) vi.findViewById(R.id.iv_order_delete);
//
//                    vi.setTag(orderHolder);
//
//                } else
//                    orderHolder = (OrdersViewHolder) vi.getTag();
//
//                orderHolder.tvPrice.setText(Const.PRICE_PEN + String.valueOf(oBanquet.getPrice()));
//                orderHolder.tvName.setText((position + 1) + ".- " + oBanquet.getName());//Delete hardcode
//
//                if (oBanquet.isFlagOptions())
//                orderHolder.tvOptions.setText("Las " + oBanquet.getOption() + " que eligió");//Delete hardcode
//                else
//                 orderHolder.tvOptions.setText("La opción elegida.");//Delete hardcode
//
//
//                orderHolder.ivDelete.setOnClickListener(this);
//                orderHolder.ivInfo.setOnClickListener(this);
//
//                break;


            case Const.CHEF:
                ChefViewHolder chefHolder;
                oChef = (ChefModel) getItem(position);

                if (vi == null) {
                    vi = layIn.inflate(R.layout.row_chef, parent, false);

                    chefHolder = new ChefViewHolder();
                    chefHolder.tvName = (TextView) vi.findViewById(R.id.tv_chef_name);
                    chefHolder.tvOptions = (TextView) vi.findViewById(R.id.tv_chef_option);
                    chefHolder.tvPrice = (TextView) vi.findViewById(R.id.tv_chef_price);
                    chefHolder.ivImage = (ImageView) vi.findViewById(R.id.iv_chef);


                    vi.setTag(chefHolder);

                } else
                    chefHolder = (ChefViewHolder) vi.getTag();

                chefHolder.tvPrice.setText(Const.PRICE_PEN + SJLStrings.format(oChef.getPrice(), SJLStrings.FORMAT_MILES_ES));
                chefHolder.tvName.setText(oChef.getName());//Delete hardcode
                chefHolder.tvOptions.setText("Taza " + Const.PRICE_PEN + SJLStrings.format(oChef.getPrice() / 3, SJLStrings.FORMAT_MILES_ES));

                if (position % 2 == 0)
                    chefHolder.ivImage.setImageResource(R.drawable.chef_1);
                else
                    chefHolder.ivImage.setImageResource(R.drawable.chef_2);

                break;

        }


        return vi;
    }

    //Click images
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_order_delete) {
            Toast.makeText(_context, "¡Eliminado!", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.iv_order_info) {
            Toast.makeText(_context, "¡Abre info!", Toast.LENGTH_SHORT).show();
        }
    }


    static class CommentViewHolder {
        TextView tvMessage;
        TextView tvDatatime;
        ImageView ivUserProfile;
    }


    static class SoupViewHolder {
        TextView tvName, tvTitle1, tvTitle2, tvTitle3, tvPrice1, tvPrice2, tvPrice3;
        ImageView ivImageSoup, ivInfo;
        Button btOrder;
    }

    static class RiceViewHolder {
        TextView tvName, tvPrice1, tvPrice2;
        ImageView ivImageRice;
        Button btOrder;
    }

    static class OrdersViewHolder {
        TextView tvName;
        TextView tvOptions;
        TextView tvPrice;
        ImageView ivDelete;
        ImageView ivInfo;
    }

    static class ChefViewHolder {
        TextView tvName, tvPrice, tvOptions;
        ImageView ivImage;
    }


    //Interfaces
    public interface OnItemClickListener {
        void onItemClick(View v, int index);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

}
