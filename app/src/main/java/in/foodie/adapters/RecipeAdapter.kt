package `in`.foodie.adapters
import `in`.foodie.database.Recipe
import `in`.foodie.databinding.ListItemRecipeBinding
import `in`.foodie.fragment.RecipeListFragmentDirections
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


/**
 * Adapter to Display Recipe List
 */
class RecipeAdapter : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(RecipeDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.apply {
            bind(createOnClickListener(recipe.recipe_id), recipe)
            itemView.tag = recipe
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(recipeId: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = RecipeListFragmentDirections.actionRecipeFragmentToRecipeDetailFragment(recipeId)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: ListItemRecipeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Recipe) {
            binding.apply {
                clickListener = listener
                recipe = item
                executePendingBindings()
            }
        }
    }
}

/**
 * checks if List contains same objects after notifyDatasetChange
 */
private class RecipeDiffCallback : DiffUtil.ItemCallback<Recipe>() {

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.recipe_id == newItem.recipe_id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }
}