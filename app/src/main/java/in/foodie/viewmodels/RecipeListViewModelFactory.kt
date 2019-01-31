package `in`.foodie.viewmodels

import `in`.foodie.database.RecipeRepository
import `in`.foodie.viewmodels.RecipeListViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * View Model Factory for [RecipeListViewModel]
 * Using Factory allows us to pass arguments to ViewModel constructor
 */
class RecipeListViewModelFactory(
    private val repository: RecipeRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = RecipeListViewModel(repository) as T
}
