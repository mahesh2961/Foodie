package `in`.foodie.pojos

import `in`.foodie.database.Recipe
import com.google.gson.annotations.SerializedName

class RecipeResponse {
    @SerializedName("recipe")
    var recipe: Recipe? = null

}