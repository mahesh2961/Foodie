package `in`.foodie.activities

import `in`.foodie.IBackPressListener
import `in`.foodie.IRecipeActivityHandler
import `in`.foodie.R
import `in`.foodie.databinding.ActivityRecipeBinding
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration

/**
 * Main Host actvitiy of the application.
 *
 */
class RecipeActivity : AppCompatActivity(),IRecipeActivityHandler{


    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var backPressListener :IBackPressListener?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityRecipeBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_recipe)

        navController = Navigation.findNavController(this, R.id.garden_nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph) //Using Nav graph for fragment navigation


    }

    /**
     * this function allows fragment to take control of Back button press event
     */
    override fun setOnBackPressListener(backPressListener: IBackPressListener?) {
        this.backPressListener=backPressListener
    }

    /**
     * Checks if any fragment is handling back button press event
     */
    override fun onBackPressed() {
      var result=backPressListener?.handleBackPress()
      if(result==false || result==null) super.onBackPressed()

    }



}