package `in`.foodie.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * DAO TO handle data access
 */
@Dao
interface RecipeDao {
    @Query("SELECT * FROM Recipe")
    fun getRecipies() : LiveData<List<Recipe>>

    /**
     * Inserting with onConflict = OnConflictStrategy.IGNORE will not insert a recipe if already available in DB
     * This will prevent duplication and will also make sure fully fetched Recipe is not overwritten
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(recipes: List<Recipe>)

    @Query("SELECT * FROM Recipe WHERE id = :recipeId")
    fun getRecipe(recipeId: String): LiveData<Recipe>

    @Query("SELECT isSynced FROM Recipe WHERE id = :recipeId")
    fun isRecipeSynced(recipeId: String): Boolean

    @Update
    fun updateRecie(recipe:Recipe)


    @Query("SELECT * FROM Recipe WHERE id = :recipeId")
    fun getRecipeObject(recipeId: String): Recipe

}