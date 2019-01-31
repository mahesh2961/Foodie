package `in`.foodie.database

import androidx.room.*

/**
 * Recipe Table
 */
@Entity(tableName = "Recipe")
data class Recipe(
        @PrimaryKey @ColumnInfo(name ="id") val recipe_id:String,
        val publisher: String,
        val f2f_url : String,
        val source_url :String="",
        val image_url :String ="",
        val social_rank: Double,
        val publisher_url : String ="",
        var isSynced : Boolean =false,
        val ingredients : List<String> = emptyList(),
        val title: String)
{

}
