package rsantillanc.sanjoylao.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.CommentModel;
import rsantillanc.sanjoylao.model.SoupModel;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 09/06/2015.
 */
public class ListViewAdapter extends BaseAdapter {

    //Comments
    private CommentModel oComment;
    //Soups
    private SoupModel oSoup;

    private List<Object> listObjects;
    private Context _context;
    private LayoutInflater layIn;
    private int labelIndex;


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
    public View getView(int position, View convertView, ViewGroup parent) {

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

                    vi.setTag(soupHolder);

                } else
                    soupHolder = (SoupViewHolder) vi.getTag();
                soupHolder.tvPrice1.setText(String.valueOf(Const.PRICE_PEN + oSoup.getPricePersonal()));
                soupHolder.tvPrice2.setText(String.valueOf(Const.PRICE_PEN + oSoup.getPriceMedium()));
                soupHolder.tvPrice3.setText(String.valueOf(Const.PRICE_PEN + oSoup.getPriceBig()));


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

        }


        return vi;
    }


    static class CommentViewHolder {
        TextView tvMessage;
        TextView tvDatatime;
        ImageView ivUserProfile;
    }


    static class SoupViewHolder {
        TextView tvName, tvTitle1, tvTitle2, tvTitle3, tvPrice1, tvPrice2, tvPrice3;
        ImageView ivImageSoup;
    }


}
