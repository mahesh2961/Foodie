package `in`.foodie.network

import `in`.foodie.FoodieApp
import `in`.foodie.IRecipeListNetworkResponse
import `in`.foodie.database.Recipe
import `in`.foodie.pojos.RecipeResponse
import `in`.foodie.utils.InjectorUtils
import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Handles request and response to and from Webservice
 */
const val TAG = "RecipeListNetworkManager"

class RecipeListNetworkManager(private val apiHandler: ApiHandler) {
    val compositeDisposable = CompositeDisposable()

    fun searchFor(query: String, responseHandler: IRecipeListNetworkResponse<List<Recipe>>) {
        compositeDisposable.clear()  //This is required to cancel previous requests for searching recipies
        if (!query.isNullOrEmpty()) {
            val subscription = apiHandler.search(query)
                    .subscribeOn(Schedulers.io())
                    .map { response -> response.recipies }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseHandler::sucessReponse, responseHandler::error)

            compositeDisposable.addAll(subscription)
        }
    }

    public fun syncRecipe(recipeId: String, responseHandler: IRecipeListNetworkResponse<Recipe?>) {
        compositeDisposable.clear()

        val subscription = apiHandler.get(recipeId)
                .subscribeOn(Schedulers.io())
                .map { recipeResponse -> recipeResponse.recipe }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseHandler::sucessReponse, responseHandler::error)

        compositeDisposable.addAll(subscription)
    }

}