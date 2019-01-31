package in.foodie.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import in.foodie.R;
import in.foodie.databinding.IngredientItemBinding;

/**
 * Ingredients View Holder of Section RecyclerView
 */
public class IngredientsViewHolder extends RecyclerView.ViewHolder {

    IngredientItemBinding binding;
    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind((itemView.findViewById(R.id.ingre_layout)));
    }

    public void bind(String title) {
      binding.setTitle(title);
    }
}
