package `in`.foodie

import `in`.foodie.database.Recipe

interface IRecipeListNetworkResponse<T> {
    fun sucessReponse(t:T)
    fun error(error: Throwable)
}