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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.concurrent.Executors

/**
 * The ViewModel used in [RecipeDetailViewModel].
 */
class RecipeDetailViewModel(
        private val recipeRepository: RecipeRepository,
        private val recipeId: String
) : ViewModel(), IRecipeListNetworkResponse<Recipe?> {

    var recipe: LiveData<Recipe>
    private val recipeMediator = MediatorLiveData<Recipe>()

    var displayMessage = MutableLiveData<String>()

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()


    fun synRecipe() {
        recipeRepository.checkAndSyncRecipe(recipeId, this)
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun postDisplayMessage(message: String?) = displayMessage.postValue(message)

    /**
     * post Recipe Details Response to View
     */
    override fun sucessReponse(response: Recipe?) {
        if (response != null) {
            response.isSynced = true //Recipe details with ingredients is now able locally, change isSync to True
            val executor = Executors.newSingleThreadExecutor()
            executor.execute {
                recipeRepository.updateRecipe(response)
                recipe = recipeRepository.getRecipe(recipeId)
                recipeMediator.postValue(response)
            }
        }

    }

    override fun error(error: Throwable) {
        postDisplayMessage(FoodieApp.instance.getString(R.string.messgae_network_error))
    }

    fun roundOf(num: Double): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(num)
    }


    init {
        recipe = recipeRepository.getRecipe(recipeId)
        recipeMediator.addSource(recipe, recipeMediator::setValue)
    }


}
