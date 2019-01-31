package in.foodie.adapters;

import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import in.foodie.FoodieApp;
import in.foodie.R;
import in.foodie.fragment.RecipeDetailsFragmentDirections;
import in.foodie.pojos.LinkModel;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Link Section of Section RecyclerView
 * This Section is responsible to display Links to Webpages
 */
public class LinkSection extends StatelessSection {
    List<LinkModel> itemList;

    public LinkSection(@NonNull List<LinkModel> itemList) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.button_item)
                .headerResourceId(R.layout.section_header)
                .build());
       this.itemList=itemList;
    }

    @Override
    public int getContentItemsTotal() {
        return itemList.size(); // number of items of this section
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder itemHolder = (HeaderViewHolder) holder;
        itemHolder.bind(FoodieApp.instance.getString(R.string.section_link_header));
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    private View.OnClickListener createOnClickListener(String url) {

        return  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeDetailsFragmentDirections.ActionRecipeFragmentToRecipeWebviewFragment  direction= RecipeDetailsFragmentDirections.actionRecipeFragmentToRecipeWebviewFragment(url);
                Navigation.findNavController(view).navigate(direction);
            }
        };

    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new LinksViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        LinksViewHolder itemHolder = (LinksViewHolder) holder;
        itemHolder.bind(itemList.get(position),createOnClickListener(itemList.get(position).getLink()));
    }


}

