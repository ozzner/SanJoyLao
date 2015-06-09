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

/**
 * Created by RenzoD on 09/06/2015.
 */
public class ListViewAdapter extends BaseAdapter {

    private CommentModel oComment;
    private List<CommentModel> commentList;
    private Context _context;
    private LayoutInflater layIn;

    public ListViewAdapter( Context _context, List<CommentModel> commentList) {
        this.commentList = commentList;
        this._context = _context;
        this.layIn = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        oComment = commentList.get(position);
        return oComment;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        CommentViewHolder holder;
        oComment = (CommentModel)getItem(position);

        if (vi == null){
            holder = new CommentViewHolder();


            vi =  layIn.inflate(R.layout.row_comments,parent,false);
            holder.tvMessage = (TextView)vi.findViewById(R.id.tv_comment);
            holder.tvDatatime = (TextView)vi.findViewById(R.id.tv_comment_time);
            holder.ivUserProfile = (ImageView)vi.findViewById(R.id.iv_comment_profile);

            vi.setTag(holder);
        }else
           holder = (CommentViewHolder)vi.getTag();


        holder.tvMessage.setText(oComment.getMessage());
        holder.tvDatatime.setText(oComment.getDatatime());

        return vi;
    }


    static class CommentViewHolder{
        TextView tvMessage;
        TextView tvDatatime;
        ImageView ivUserProfile;
    }
}
