package in.foodie.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import in.foodie.R;
import in.foodie.databinding.SectionHeaderBinding;

/**
 * View holder for Header in Section Recycler View
 */
public class HeaderViewHolder extends RecyclerView.ViewHolder {

    SectionHeaderBinding binding;
    public HeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind((itemView.findViewById(R.id.header_layout)));
    }

    public void bind(String title) {
        binding.setTitle(title);
    }
}
