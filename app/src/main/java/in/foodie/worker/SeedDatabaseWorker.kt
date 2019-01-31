package `in`.foodie.worker

import `in`.foodie.database.AppDatabase
import `in`.foodie.pojos.RecipeSearchResponse
import `in`.foodie.utils.RECIPE_DATA_FILENAME
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader

/**
 * Class to load re-load DB from json file.
 * This is used for testing purpose only
 */
class SeedDatabaseWorker(
        context: Context,
        workerParams: WorkerParameters
) : Worker(context, workerParams) {

    private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }

    override fun doWork(): Result {
        return try {
            applicationContext.assets.open(RECIPE_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val type = object : TypeToken<RecipeSearchResponse>() {}.type
                    val recipeSearchResponse: RecipeSearchResponse = Gson().fromJson(jsonReader, type)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.recipeDao().insertAll(recipeSearchResponse.recipies)
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }
}