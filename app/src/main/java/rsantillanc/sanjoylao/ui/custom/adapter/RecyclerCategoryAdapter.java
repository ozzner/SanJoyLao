package rsantillanc.sanjoylao.ui.custom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.CategoryModel;

/**
 * Created by RenzoD on 03/06/2015.
 */
public class RecyclerCategoryAdapter extends RecyclerView.Adapter<RecyclerCategoryAdapter.BanquetViewHolder> {

    private LayoutInflater layIn;
    private List<CategoryModel> categories = Collections.EMPTY_LIST;
    private OnItemClickListener mItemClickListener;
    private Context _context;


    public RecyclerCategoryAdapter(List<CategoryModel> items, Context ctx) {
        this.layIn = LayoutInflater.from(ctx);
        this.categories = items;
        this._context = ctx;
    }

    @Override
    public BanquetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ui = layIn.inflate(R.layout.item_category, parent, false);
        return new BanquetViewHolder(ui);
    }

    @Override
    public void onBindViewHolder(BanquetViewHolder holder, int index) {
        holder.tvName.setText(categories.get(index).getName());

        if (categories.get(index).getImage() != null){
            Picasso.with(_context)
                    .load(categories.get(index).getImage().getUrl())
                    .into(holder.ivImage);
        }


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    class BanquetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName;
        ImageView ivImage;
        ImageView ivIcon;

        public BanquetViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_category_name);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_category_image);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_category_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(categories.get(getPosition()));
        }
    }


    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mItemClickListener = mListener;
    }

    public interface OnItemClickListener {
        void onItemClick(CategoryModel category);
    }


}
