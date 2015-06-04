package rsantillanc.sanjoylao.custom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.OptionsModel;

/**
 * Created by RenzoD on 03/06/2015.
 */
public class RecyclerViewOptionsAdapter extends RecyclerView.Adapter<RecyclerViewOptionsAdapter.OptionsViewHolder> {

    private LayoutInflater layIn;
    private List<OptionsModel> optionsItems = Collections.EMPTY_LIST;
    private Context ctx;
    OnItemClickListener mItemClickListener;


    public RecyclerViewOptionsAdapter(List<OptionsModel> optionsItems, Context ctx) {
        this.layIn = LayoutInflater.from(ctx);
        this.optionsItems = optionsItems;
        this.ctx = ctx;
    }

    @Override
    public OptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View banquet = layIn.inflate(R.layout.row_item_options,parent,false);
        return new OptionsViewHolder(banquet);
    }

    @Override
    public void onBindViewHolder(OptionsViewHolder holder, final int index) {
       OptionsModel options = optionsItems.get(index);
       holder.tvPlate1.setText(options.getPlateName1());
       holder.tvPlate1.setText(options.getPlateName1());
       holder.tvPlate2.setText(options.getPlateName2());
       holder.btPedir.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(v.getContext(),"Click " + index, Toast.LENGTH_SHORT).show();
           }
       });

    }

    @Override
    public int getItemCount() {
        return optionsItems.size();
    }


    class OptionsViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
        TextView tvTitle;
        TextView tvPlate1;
        TextView tvPlate2;
        Button btPedir;

        public OptionsViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_options_title);
            tvPlate1 = (TextView) itemView.findViewById(R.id.tv_plate1);
            tvPlate2 = (TextView) itemView.findViewById(R.id.tv_plate2);
            btPedir = (Button) itemView.findViewById(R.id.bt_options_pedir);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(v,getPosition());
        }
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mItemClickListener = mListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View v,int index);
    }


}
