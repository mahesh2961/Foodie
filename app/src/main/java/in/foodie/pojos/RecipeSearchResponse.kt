package `in`.foodie.pojos

import `in`.foodie.database.Recipe
import com.google.gson.annotations.SerializedName

class RecipeSearchResponse {
    @SerializedName("recipes")
    var recipies: List<Recipe> = emptyList();

    @SerializedName("count")
    var count: Int = 0;
}