package `in`.foodie.database

import `in`.foodie.IRecipeListNetworkResponse
import `in`.foodie.network.NetworkModule
import `in`.foodie.network.RecipeListNetworkManager
import java.util.concurrent.Executors

/**
 * Repository to provide recipes from Database or web Service
 */
class RecipeRepository private constructor(private val recipeDao: RecipeDao) {


    fun getRecipies() = recipeDao.getRecipies()

    fun getRecipeObject(recipeId: String) = recipeDao.getRecipeObject(recipeId)


    fun getRecipe(recipeId: String) = recipeDao.getRecipe(recipeId)

    val networkLayer = RecipeListNetworkManager(NetworkModule().buildNetworkApi())


    fun insertData(newList: List<Recipe>) = recipeDao.insertAll(newList)

    /**
     * Searches for Recipies based on search query
     */
    fun searchFor(query: String, handler: IRecipeListNetworkResponse<List<Recipe>>) = networkLayer.searchFor(query, handler)

    fun isRecipeSynced(recipeId: String) = recipeDao.isRecipeSynced(recipeId)

    fun syncRecipe(recipeId: String, handler: IRecipeListNetworkResponse<Recipe?>) = networkLayer.syncRecipe(recipeId, handler)

    fun updateRecipe(recipe: Recipe) = recipeDao.updateRecie(recipe)

    /**
     * Checks if Complete Recipe Detail is available in local Database
     * Fetchs Recipe using isSync variable stored in DB
     */
    fun checkAndSyncRecipe(recipeId: String, handler: IRecipeListNetworkResponse<Recipe?>) {
        val executor = Executors.newSingleThreadExecutor()  //This can be handled better using JOB SHEDULERS
        executor.execute {
            if (isRecipeSynced(recipeId)) handler.sucessReponse(getRecipeObject(recipeId))
            else syncRecipe(recipeId, handler)
        }
    }


    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: RecipeRepository? = null

        fun getInstance(recipeDao: RecipeDao) =
                instance ?: synchronized(this) {
                    instance ?: RecipeRepository(recipeDao).also { instance = it }
                }
    }

}