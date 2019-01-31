package `in`.foodie.viewmodels

import `in`.foodie.database.RecipeRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * View Model Factory for RecipeDetailsFragment
 * Using Factory allows us to pass arguments to ViewModel constructor
 */
class RecipeDetailsViewModelFactory(
        private val repository: RecipeRepository,
        private val recipeId :String
): ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>) = RecipeDetailViewModel(repository,recipeId) as T
}