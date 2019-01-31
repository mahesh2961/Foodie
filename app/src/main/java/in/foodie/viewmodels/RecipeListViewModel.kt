package `in`.foodie.viewmodels

import `in`.foodie.FoodieApp
import `in`.foodie.IRecipeListNetworkResponse
import `in`.foodie.R
import `in`.foodie.database.Recipe
import `in`.foodie.database.RecipeRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.Executors


class RecipeListViewModel internal constructor(
        private val recipeRepository: RecipeRepository
) : ViewModel(), IRecipeListNetworkResponse<List<Recipe>> {

    private val recipeList = MediatorLiveData<List<Recipe>>()

    //This is need to display message incase of error or to display loading while fetching data from web service
    var dislayMessage = MutableLiveData<String>()

    lateinit var  liveRecipeList:LiveData<List<Recipe>>
    init {
         liveRecipeList = recipeRepository.getRecipies()
        recipeList.addSource(liveRecipeList, recipeList::setValue)
    }

    fun getRecipes() = recipeList

    fun diplayAvailableRecipe() {
        postDisplayMessage(null)
        recipeList.removeSource(liveRecipeList)
        liveRecipeList = recipeRepository.getRecipies()
        recipeList.addSource(liveRecipeList,recipeList::setValue)
    }

    private fun searchFor(query: String) {
        postDisplayMessage(getStringResouce(R.string.messgae_loading))
        recipeRepository.searchFor(query, this)
    }

    fun search(query: String)
    {
        searchFor(query)
        if(query.isNullOrEmpty()) diplayAvailableRecipe()
    }

    private fun postDisplayMessage(message:String?) =dislayMessage.postValue(message)


    override fun sucessReponse(list: List<Recipe>) {

        val executor = Executors.newSingleThreadExecutor()
        executor.execute(Runnable {
            recipeRepository.insertData(list)
        })

        if (!list.isNullOrEmpty())
        {
            recipeList.postValue(list)
            postDisplayMessage(null)
        }
        else postDisplayMessage(getStringResouce(R.string.no_recipes_found))
    }

    fun getStringResouce(id :Int)= FoodieApp.instance.getString(id)

    override fun error(error: Throwable) {
        postDisplayMessage(getStringResouce(R.string.messgae_network_error))
    }
}


