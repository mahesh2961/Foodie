package `in`.foodie.fragment

import `in`.foodie.R
import `in`.foodie.adapters.IngredientsSection
import `in`.foodie.adapters.RecipeAdapter
import `in`.foodie.databinding.FragmentRecipeListBinding
import `in`.foodie.utils.InjectorUtils
import `in`.foodie.viewmodels.RecipeListViewModel
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miguelcatalan.materialsearchview.MaterialSearchView
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import androidx.databinding.adapters.SearchViewBindingAdapter.setOnQueryTextListener
import androidx.recyclerview.widget.SimpleItemAnimator
import android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * Fragment o display List of Recipe
 */
const val TAG="RecipeListFragment"
class RecipeListFragment : Fragment()
{
    private lateinit var viewModel: RecipeListViewModel
    private lateinit var searchView: MaterialSearchView
    private lateinit var binding:FragmentRecipeListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =  FragmentRecipeListBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root

        val factory = InjectorUtils.provideRecipeListViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(RecipeListViewModel::class.java)

        val adapter = RecipeAdapter()
        binding.recipeList.adapter = adapter
        searchView=binding.searchView
        binding.toolbar.inflateMenu(R.menu.fragment_recipe_list_menu)
        (binding.recipeList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations=false

        subscribeUi(adapter)
        setupSearchView()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        searchView?.closeSearch();

    }

    /**
     * Sets up search view
     */
    private fun setupSearchView(){
        searchView.setMenuItem(binding.toolbar.menu!!.findItem(R.id.action_search))

        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.search(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                /**
                 * Search will always get data from Web service.
                 * Data fetched will be stored in Database with isSynced value as false.
                 * Later when the user clicks a particular recipe, recipe details are fetch in RecipeDetailsFragment and value of isSynced
                 * is changed to true
                 *
                 * If query is empty, search will return recipes (if any) available in database
                 */
                viewModel.search(query)
                return false
            }
        })


        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
            }
            override fun onSearchViewClosed() {
                /**
                 * search view is closed, display cache data if Recyclerview is empty
                 */
                if(binding.recipeList.adapter?.itemCount==0)viewModel.diplayAvailableRecipe()

            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView?.closeSearch();
    }

    /**
     * Waits for updates to Model from ViewModel and applies changes accordingly
     */
    private fun subscribeUi(adapter: RecipeAdapter) {
        viewModel.getRecipes().observe(viewLifecycleOwner, Observer { recipies ->
            Log.i(TAG,"recipe loading")
            if (recipies != null) adapter.submitList(recipies)
        })

        /**
         * display error,warning,info on screen
         */
        viewModel.dislayMessage.observe(viewLifecycleOwner, Observer { displayMsg ->
            Log.i(TAG,"error loading")
            binding.isError=!displayMsg.isNullOrEmpty()
           binding.displayMessage=displayMsg  // Data binding
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        val item = menu!!.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        super.onCreateOptionsMenu(menu,inflater)
    }


}