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
import rsantillanc.sanjoylao.model.NavigationDrawerModel;

/**
 * Created by RenzoD on 02/06/2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder>{

    private LayoutInflater layIn;
    private List<NavigationDrawerModel> navItems = Collections.EMPTY_LIST;
    private Context ctx;


    public NavigationDrawerAdapter( List<NavigationDrawerModel> navItems, Context ctx) {
        this.layIn = LayoutInflater.from(ctx);
        this.navItems = navItems;
        this.ctx = ctx;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = layIn.inflate(R.layout.row_navigation_drawer,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int index) {
       NavigationDrawerModel model =  navItems.get(index);
        myViewHolder.tvTitle.setText(model.getTitle());
        myViewHolder.ivIcon.setImageResource(model.getIcon());
    }

    @Override
    public int getItemCount() {
        return navItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_icons_nav);
        }
    }
}
