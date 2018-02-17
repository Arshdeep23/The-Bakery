package com.example.thebakery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thebakery.Desc.BakeryDetailedList;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BakeryHomeAdapter extends RecyclerView.Adapter<BakeryHomeAdapter.ViewHolder>{

    private Context myContext;

    public BakeryHomeAdapter(Context myContext) {
        this.myContext = myContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.bakery_item_card_title) TextView bakery_recipe;
        @BindView(R.id.bakery_item_card_image) ImageView bakery_recipe_image_load;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public int getItemCount() {
        return TheBakeryActivity.bakery_dishNames.length;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bakery_recipe.setText(TheBakeryActivity.bakery_dishNames[position]);
        if (TheBakeryActivity.dishImage[position] != "") {
            Glide.with(myContext)
                    .load(TheBakeryActivity.dishImage[position])
                    .into(holder.bakery_recipe_image_load);
        }
        holder.bakery_recipe_image_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(myContext, BakeryDetailedList.class);
                i.putExtra("id",position);
                myContext.startActivity(i);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bakery_card, parent, false);
        return new ViewHolder(rootView);
    }
}
