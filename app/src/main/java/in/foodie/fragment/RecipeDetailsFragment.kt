package `in`.foodie.fragment

import `in`.foodie.R
import `in`.foodie.adapters.IngredientsSection
import `in`.foodie.adapters.LinkSection
import `in`.foodie.adapters.RecipeAdapter
import `in`.foodie.database.Recipe
import `in`.foodie.databinding.FragmentRecipeDetailBinding
import `in`.foodie.pojos.LinkModel
import `in`.foodie.utils.InjectorUtils
import `in`.foodie.viewmodels.RecipeDetailViewModel
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter

/**
 * Fragment to Display Recipe Details
 */
class RecipeDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val recipeId = RecipeDetailsFragmentArgs.fromBundle(arguments!!).recipeId

        val factory = InjectorUtils.provideRecipeDetailViewModelFactory(requireActivity(), recipeId)
        val recipeDetailViewModel = ViewModelProviders.of(this, factory)
                .get(RecipeDetailViewModel::class.java)

        var binding = DataBindingUtil.inflate<FragmentRecipeDetailBinding>(
                inflater, R.layout.fragment_recipe_detail, container, false).apply {
            viewModel = recipeDetailViewModel
            setLifecycleOwner(this@RecipeDetailsFragment)
        }

        recipeDetailViewModel.synRecipe()
        subscribeUi(recipeDetailViewModel,binding)

        binding.toolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        return binding.root
    }

    /**
     * Sets Section Adapter
     */
    private fun setAdapter(recipe :Recipe,binding:FragmentRecipeDetailBinding)
    {
        var section: IngredientsSection? = null
        val linklist: MutableList<LinkModel> = mutableListOf<LinkModel>();
        val sectionAdapter = SectionedRecyclerViewAdapter();

        if (!recipe.ingredients.isNullOrEmpty()) {
            section = IngredientsSection(recipe.ingredients)
            sectionAdapter.addSection(section)
        }
        val link1 = LinkModel(1, recipe.source_url, getString(R.string.view_instruction_title))
        val link2 = LinkModel(2, recipe.f2f_url, getString(R.string.view_original_title))
        linklist.add(link1)
        linklist.add(link2)

        val linkSection = LinkSection(linklist);
        sectionAdapter.addSection(linkSection)
        binding.sectionList.adapter = sectionAdapter
    }

    /**
     * Waits for updates to Model from ViewModel and applies changes accordingly
     */
    private fun subscribeUi(recipeDetailViewModel: RecipeDetailViewModel,binding: FragmentRecipeDetailBinding) {

        recipeDetailViewModel.recipe.observe(viewLifecycleOwner, Observer { recipe ->
            setAdapter(recipe,binding)

        })

        recipeDetailViewModel.displayMessage.observe(viewLifecycleOwner, Observer { displayMessage  ->
            val snackbar = Snackbar.make(
                    binding.root,
                    displayMessage,
                    Snackbar.LENGTH_SHORT
            )
            snackbar.show()
        })
    }


}