package rsantillanc.sanjoylao.ui.custom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.CommentModel;
import rsantillanc.sanjoylao.util.SJLDates;
import rsantillanc.sanjoylao.util.SJLStrings;

/**
 * Created by RenzoD on 03/06/2015.
 */
public class RecyclerCommentAdapter extends RecyclerView.Adapter<RecyclerCommentAdapter.CommentViewHolder> {

    private LayoutInflater layIn;
    private List<CommentModel> comments = Collections.EMPTY_LIST;
    private Context ctx;



    public RecyclerCommentAdapter(List<CommentModel> list, Context ctx) {
        this.layIn = LayoutInflater.from(ctx);
        this.comments = list;
        this.ctx = ctx;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(layIn.inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int index) {
        CommentModel item = comments.get(index);

        if (item.getUser().getUrlProfileImage() != null)
            setUserImage(holder.civPhoto, item.getUser().getUrlProfileImage());

        holder.tvDate.setText(SJLDates.customDateConverter(item.getCreatedAt().toString().toUpperCase().trim()
                , SJLStrings.PARSE_DATE_FORMAT
                , SJLDates.FORMAT_DATE).toUpperCase());

        holder.tvMessage.setText(item.getComment().trim().toString());
        holder.tvUsername.setText(item.getUser().getFullName().toString());
    }

    private void setUserImage(CircleImageView civPhoto, String url) {
        Picasso.with(ctx)
                .load(url)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(civPhoto);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView civPhoto;
        private TextView tvUsername;
        private TextView tvMessage;
        private TextView tvDate;


        public CommentViewHolder(View vi) {
            super(vi);

            civPhoto = (CircleImageView) vi.findViewById(R.id.iv_comment_user_image);
            tvUsername = (TextView) vi.findViewById(R.id.tv_comment_username);
            tvMessage = (TextView) vi.findViewById(R.id.tv_comment_message);
            tvDate = (TextView) vi.findViewById(R.id.tv_comment_date);

        }

        @Override
        public void onClick(View v) {

        }
    }


//    public void setOnItemClickListener(OnOrderItemClickListener mListener) {
//        this.mItemClickListener = mListener;
//    }

//    public interface OnOrderItemClickListener {
//        void onItemClick(View v, int index);
//
//        void onOpenImage(PlateSizeModel plateSize);
//
//        void onOpenIngredients(PlateModel plate);
//
//        void onIncrementCount();
//
//        void onDecrementCount();
//
//        void onDeleteItem(OrderDetailModel orderDetailModel);
//    }

}
