package `in`.foodie

import `in`.foodie.IBackPressListener

interface IRecipeActivityHandler {
    fun setOnBackPressListener(backPressListener: IBackPressListener?)
}