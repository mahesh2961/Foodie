package `in`.foodie.utils

import `in`.foodie.database.AppDatabase
import `in`.foodie.database.RecipeRepository
import `in`.foodie.viewmodels.*
import android.content.Context

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    public fun getRecipeRepository(context: Context): RecipeRepository {
        return RecipeRepository.getInstance(AppDatabase.getInstance(context).recipeDao())
    }

    fun provideRecipeListViewModelFactory(context: Context): RecipeListViewModelFactory {
        val repository = getRecipeRepository(context)
        return RecipeListViewModelFactory(repository)
    }

    fun provideRecipeDetailViewModelFactory(
            context: Context,recipeId: String
    ): RecipeDetailsViewModelFactory {
        return RecipeDetailsViewModelFactory(getRecipeRepository(context),recipeId)
    }


    fun providWebViewViewModelFactory(
            url:String
    ): WebViewViewModelFactory {
        return WebViewViewModelFactory(url)
    }

}
