package in.foodie.adapters;

import android.view.View;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.foodie.FoodieApp;
import in.foodie.R;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Ingredients Section of Section RecyclerView
 */
public class IngredientsSection extends StatelessSection {
    List<String> itemList; //List of Ingredients

    public IngredientsSection(@NonNull List<String> itemList) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.ingredient_item)
                .headerResourceId(R.layout.section_header)
                .build());
        this.itemList=itemList;
    }

    @Override
    public int getContentItemsTotal() {
        return itemList==null?0:itemList.size(); // number of items of this section
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder itemHolder = (HeaderViewHolder) holder;
        itemHolder.bind(FoodieApp.instance.getString(R.string.section_ingredients_header));
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }



    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        IngredientsViewHolder itemHolder = (IngredientsViewHolder) holder;
        itemHolder.bind(itemList.get(position));
    }
}
