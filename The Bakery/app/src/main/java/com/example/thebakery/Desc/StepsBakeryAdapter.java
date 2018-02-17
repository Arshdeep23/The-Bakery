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

public class StepsBakeryAdapter extends RecyclerView.Adapter<StepsBakeryAdapter.StepViewHolder>{

    private static int myTimes;
    private static int myIndex;

    final private ListItemClickListener myBakeryOnClickListener;


    private int my_number_items;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public StepsBakeryAdapter(int index, int times, ListItemClickListener listener) {
        myIndex =index;
        myTimes =times;
        myBakeryOnClickListener =listener;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        int steps_bakery_item_design = R.layout.steps_bakery_item_design;
        LayoutInflater layoutInflater = LayoutInflater.from(parentContext);
        boolean attach_to_parent = false;

        View rootView = layoutInflater.inflate(steps_bakery_item_design, parent, attach_to_parent);
        StepViewHolder my_stepViewHolder=new StepViewHolder(rootView);
        rootView.setTag(my_stepViewHolder);

        return my_stepViewHolder;
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return myTimes;
    }

    class StepViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        @BindView(R.id.bakery_step_name) TextView bakery_recipe_steps;

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            myBakeryOnClickListener.onListItemClick(adapterPosition);
        }

        void bind(int listIndex) {
            bakery_recipe_steps.setText(TheBakeryActivity.shortDescription[myIndex][listIndex]);
        }

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

    }

}
