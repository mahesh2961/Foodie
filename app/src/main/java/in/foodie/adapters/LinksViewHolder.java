package in.foodie.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import in.foodie.R;
import in.foodie.databinding.ButtonItemBinding;
import in.foodie.pojos.LinkModel;

public class LinksViewHolder extends RecyclerView.ViewHolder {

    ButtonItemBinding binding;
    public LinksViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind((itemView.findViewById(R.id.link_layout)));
    }

    public void bind(LinkModel model,View.OnClickListener listener) {
        binding.setLinkmodel(model);
        binding.setClickListener(listener);
    }
}
