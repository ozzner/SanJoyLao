package rsantillanc.sanjoylao.custom.adapter;

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
import rsantillanc.sanjoylao.model.BanquetModel;
import rsantillanc.sanjoylao.model.CommentModel;
import rsantillanc.sanjoylao.model.RiceModel;
import rsantillanc.sanjoylao.model.SoupModel;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.Strings;

/**
 * Created by RenzoD on 09/06/2015.
 */
public class ListViewAdapter extends BaseAdapter implements View.OnClickListener {

    //Comments
    private CommentModel oComment;
    //Soups
    private SoupModel oSoup;
    //Banquet
    private BanquetModel oBanquet;
    //Rice
    private RiceModel oRice;

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
        Object obj = null;
        obj = listObjects.get(position);
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
                    soupHolder.tvName = (TextView) vi.findViewById(R.id.tv_name);
                    soupHolder.tvTitle1 = (TextView) vi.findViewById(R.id.tv_title1);
                    soupHolder.tvTitle2 = (TextView) vi.findViewById(R.id.tv_title2);
                    soupHolder.tvTitle3 = (TextView) vi.findViewById(R.id.tv_title3);
                    soupHolder.tvPrice1 = (TextView) vi.findViewById(R.id.tv_price1);
                    soupHolder.tvPrice2 = (TextView) vi.findViewById(R.id.tv_price2);
                    soupHolder.tvPrice3 = (TextView) vi.findViewById(R.id.tv_price3);
                    soupHolder.btOrder = (Button) vi.findViewById(R.id.bt_order_soup);
                    final String[] array = {"Personal " + soupHolder.tvPrice1.getText().toString(),
                            "Mediana " + soupHolder.tvPrice2.getText().toString(),
                            "Grande " + soupHolder.tvPrice3.getText().toString()};

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

                    vi.setTag(soupHolder);

                } else
                    soupHolder = (SoupViewHolder) vi.getTag();

                soupHolder.tvName.setText(oSoup.getTitle());
                soupHolder.tvPrice1.setText(Const.PRICE_PEN + String.format("%1.2f", oSoup.getPricePersonal()));
                soupHolder.tvPrice2.setText(Const.PRICE_PEN + String.format("%1.2f", oSoup.getPriceMedium()));
                soupHolder.tvPrice3.setText(Const.PRICE_PEN + String.format("%1.2f", oSoup.getPriceBig()));

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

                vi.setTag(riceHolder);

            } else
                riceHolder = (RiceViewHolder) vi.getTag();

                riceHolder.tvName.setText(oRice.getTitle());
                riceHolder.tvPrice1.setText(Const.PRICE_PEN + Strings.format(oRice.getPriceTaza(), Strings.FORMAT_MILES));
                riceHolder.tvPrice2.setText(Const.PRICE_PEN + Strings.format(oRice.getPriceFuente(), Strings.FORMAT_MILES));


            if (position % 2 == 0)
                riceHolder.ivImageRice.setImageResource(R.drawable.plate_1);
            else
                riceHolder.ivImageRice.setImageResource(R.drawable.plate_2);

            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v, position);
                }
            });

            break;

            case Const.COMMENTS:

                CommentViewHolder holder;
                oComment = (CommentModel) getItem(position);

                if (vi == null) {
                    holder = new CommentViewHolder();


                    vi = layIn.inflate(R.layout.row_comments, parent, false);
                    holder.tvMessage = (TextView) vi.findViewById(R.id.tv_comment);
                    holder.tvDatatime = (TextView) vi.findViewById(R.id.tv_comment_time);
                    holder.ivUserProfile = (ImageView) vi.findViewById(R.id.iv_comment_profile);

                    vi.setTag(holder);
                } else
                    holder = (CommentViewHolder) vi.getTag();


                holder.tvMessage.setText(oComment.getMessage());
                holder.tvDatatime.setText(oComment.getDatatime());
                break;

            case Const.ORDERS:
                //TODO borrar luego ya que no es correcto, solo es por pruebas.
                OrdersViewHolder orderHolder = null;
                oBanquet = (BanquetModel) getItem(position);

                if (vi == null) {
                    vi = layIn.inflate(R.layout.row_orders, parent, false);

                    orderHolder = new OrdersViewHolder();
                    orderHolder.tvName = (TextView) vi.findViewById(R.id.tv_order_name);
                    orderHolder.tvOptions = (TextView) vi.findViewById(R.id.tv_order_option);
                    orderHolder.tvPrice = (TextView) vi.findViewById(R.id.tv_order_price);
                    orderHolder.ivInfo = (ImageView) vi.findViewById(R.id.iv_order_info);
                    orderHolder.ivDelete = (ImageView) vi.findViewById(R.id.iv_order_delete);

                    vi.setTag(orderHolder);

                }else
                    orderHolder = (OrdersViewHolder)vi.getTag();

                orderHolder.tvPrice.setText(Const.PRICE_PEN + String.valueOf(oBanquet.getPrice()));
                orderHolder.tvName.setText((position+1) + ".- " + oBanquet.getName());//Delete hardcode
                orderHolder.tvOptions.setText(oBanquet.getOption());
                orderHolder.ivDelete.setOnClickListener(this);
                orderHolder.ivInfo.setOnClickListener(this);

                break;

        }


        return vi;
    }

    //Click images
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_order_delete){
            Toast.makeText(_context,"¡Eliminado!",Toast.LENGTH_SHORT).show();
        }else if (v.getId() == R.id.iv_order_info){
            Toast.makeText(_context,"¡Abre info!",Toast.LENGTH_SHORT).show();
        }
    }


    static class CommentViewHolder {
        TextView tvMessage;
        TextView tvDatatime;
        ImageView ivUserProfile;
    }


    static class SoupViewHolder {
        TextView tvName, tvTitle1, tvTitle2, tvTitle3, tvPrice1, tvPrice2, tvPrice3;
        ImageView ivImageSoup;
        Button btOrder;
    }

    static class RiceViewHolder {
        TextView tvName,tvPrice1, tvPrice2;
        ImageView ivImageRice;
    }

    static class OrdersViewHolder {
        TextView tvName;
        TextView tvOptions;
        TextView tvPrice;
        ImageView ivDelete;
        ImageView ivInfo;
    }


    //Interfaces
    public interface OnItemClickListener{
        void onItemClick(View v, int index);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

}
