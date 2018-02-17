package com.example.thebakery.Desc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thebakery.TheBakeryActivity;
import com.example.thebakery.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BakeryIngredAdapter extends RecyclerView.Adapter<BakeryIngredAdapter.IngredViewHolder>{

    private static int myTimes;
    private static int myIndex;

    public BakeryIngredAdapter(int index, int times) {
        myIndex = index;
        myTimes = times;
    }



    @Override
    public void onBindViewHolder(IngredViewHolder holder, int position) {
        holder.bind(position);
    }



    class IngredViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.bakery_quantity) TextView my_quantity_text_view;
        @BindView(R.id.bakery_measure) TextView my_measure_text_view;
        @BindView(R.id.bakery_ingredients_name) TextView my_ingred_text_view;


        public IngredViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        void bind(int listIndex) {
            my_ingred_text_view.setText(TheBakeryActivity.ingredient[myIndex][listIndex]);
            my_quantity_text_view.setText(TheBakeryActivity.quantity[myIndex][listIndex]);
            my_measure_text_view.setText(TheBakeryActivity.measure[myIndex][listIndex]);
        }
    }
    @Override
    public IngredViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context myContext = parent.getContext();
        int my_layout_list_item = R.layout.ingredient_bakery_item_design;
        LayoutInflater my_layoutInflater = LayoutInflater.from(myContext);
        boolean attach_to_parent = false;

        View rootView = my_layoutInflater.inflate(my_layout_list_item, parent, attach_to_parent);
        IngredViewHolder my_ingredViewHolder = new IngredViewHolder(rootView);
        rootView.setTag(my_ingredViewHolder);

        return my_ingredViewHolder;
    }
    @Override
    public int getItemCount() {
        return myTimes;
    }
}
