package rsantillanc.sanjoylao.ui.custom.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLStrings;

/**
 * Created by RenzoD on 03/06/2015.
 */
public class RecyclerPlateAdapter extends RecyclerView.Adapter<RecyclerPlateAdapter.PlateHolder> {

    private final Activity mActivity;
    private LayoutInflater layIn;
    private List<PlateModel> plates = Collections.EMPTY_LIST;
    private OnItemPlateClickListener mItemClickListener;
    private Context _context;
    private int currentIndex;


    public RecyclerPlateAdapter(List<PlateModel> items, Activity activity) {
        this.layIn = LayoutInflater.from(activity);
        this.plates = items;
        this._context = activity;
        this.mActivity = activity;
    }

    @Override
    public PlateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ui = layIn.inflate(R.layout.item_plate, parent, false);
        return new PlateHolder(ui);
    }

    @Override
    public void onBindViewHolder(PlateHolder holder, int index) {

        PlateModel plate = plates.get(index);
        TextView[] names = {holder.tvName1, holder.tvName2, holder.tvName3};
        TextView[] prices = {holder.tvPrice1, holder.tvPrice2, holder.tvPrice3};

        holder.tvPlateName.setText(plate.getName());

        //Hide all
        for (int i = 0; i < names.length; i++) {
            hideVisibility(names[i], prices[i]);
        }

        //Show only with values
        for (int i = 0; i < plate.getPlateSize().size(); i++) {
            printValues(names[i], prices[i], plate.getPlateSize().get(i));
        }

    }

    private void printValues(TextView name, TextView price, PlateSizeModel plateSize) {

        //Enabled visibility
        name.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);

        //Set values
        name.setText(plateSize.getSize().getName());
        price.setText(Const.PRICE_PEN + SJLStrings.format(plateSize.getPrice(), SJLStrings.FORMAT_MILES_EN));

    }

    private void hideVisibility(TextView name, TextView price) {
        //Enabled visibility
        name.setVisibility(View.INVISIBLE);
        price.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return plates.size();
    }


    private void showPopupMenu(View v, int position, final PlateModel plate) {
        Context wrapper = new ContextThemeWrapper(_context, R.style.PopupMenu);
        final PopupMenu popup = new PopupMenu(wrapper, v);
        popup.getMenuInflater().inflate(R.menu.menu_size_popup, popup.getMenu());

        int index = 0;
        popup.getMenu().clear();
        for (PlateSizeModel plateSize : plates.get(position).getPlateSize()) {
            popup.getMenu().add(Menu.NONE, index, Menu.NONE, plateSize.getSize().getName())
                    .setIcon(_context.getResources().getDrawable(R.drawable.ic_info_plate));
            index++;
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mItemClickListener.onPopupItemClick(item,plate.getPlateSize().get(item.getItemId()));
                return false;
            }
        });

        // Force icons to show
        Object menuHelper;
        Class[] argTypes;

        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popup);
            argTypes = new Class[]{boolean.class};
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            // Possible exceptions are NoSuchMethodError and NoSuchFieldError
            //
            // In either case, an exception indicates something is wrong with the reflection code, or the
            // structure of the PopupMenu class or its dependencies has changed.
            //
            // These exceptions should never happen since we're shipping the AppCompat library in our own apk,
            // but in the case that they do, we simply can't force icons to display, so log the error and
            // show the menu normally.
            popup.show();
            return;
        }

        popup.show();

        moveToLeftPopupMenu(menuHelper, argTypes);
    }

    private void moveToLeftPopupMenu(Object menuHelper, Class[] argTypes) {

        // Try to force some horizontal offset
        try {
            Field fListPopup = menuHelper.getClass().getDeclaredField("mPopup");
            fListPopup.setAccessible(true);
            Object listPopup = fListPopup.get(menuHelper);
            argTypes = new Class[]{int.class};
            Class listPopupClass = listPopup.getClass();

            // Get the width of the popup window
            int width = (Integer) listPopupClass.getDeclaredMethod("getWidth").invoke(listPopup);

            // Invoke setHorizontalOffset() with the negative width to move left by that distance
            listPopupClass.getDeclaredMethod("setHorizontalOffset", argTypes).invoke(listPopup, -width);

            // Invoke show() to update the window's position
            listPopupClass.getDeclaredMethod("show").invoke(listPopup);
        } catch (Exception e) {
            // Again, an exception here indicates a programming error rather than an exceptional condition
            // at runtime
        }
    }


    class PlateHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvPlateName;
        TextView tvPrice1;
        TextView tvPrice2;
        TextView tvPrice3;
        TextView tvName1;
        TextView tvName2;
        TextView tvName3;
        ImageView ivAddPlate;

        public PlateHolder(View itemView) {
            super(itemView);

            tvPlateName = (TextView) itemView.findViewById(R.id.tv_plate_name);
            tvPrice1 = (TextView) itemView.findViewById(R.id.tv_plate_price1);
            tvPrice2 = (TextView) itemView.findViewById(R.id.tv_plate_price2);
            tvPrice3 = (TextView) itemView.findViewById(R.id.tv_plate_price3);
            tvName1 = (TextView) itemView.findViewById(R.id.tv_plate_size_name1);
            tvName2 = (TextView) itemView.findViewById(R.id.tv_plate_size_name2);
            tvName3 = (TextView) itemView.findViewById(R.id.tv_plate_size_name3);
            ivAddPlate = (ImageView) itemView.findViewById(R.id.iv_plate_add);

            //Click
            ivAddPlate.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {

                if (v instanceof ImageView)
                    showPopupMenu(v, getPosition(), plates.get(getPosition()));
                else
                    mItemClickListener.onItemClick(plates.get(getPosition()));

            }
        }
    }


    public void setOnItemPlateClickListener(OnItemPlateClickListener mListener) {
        this.mItemClickListener = mListener;
    }

    public interface OnItemPlateClickListener {
        void onItemClick(PlateModel plate);

        void onAddPlateClick(View v);

        void onPopupItemClick(MenuItem item, PlateSizeModel plateSize);
    }


}
