package `in`.foodie.fragment

import `in`.foodie.IBackPressListener
import `in`.foodie.IRecipeActivityHandler
import `in`.foodie.R
import `in`.foodie.databinding.WebviewFragmentBinding
import `in`.foodie.utils.InjectorUtils
import `in`.foodie.viewmodels.WebViewViewModel
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment to Display web site in Webview
 */
class WebViewFragment : Fragment(),IBackPressListener {

    var handler :IRecipeActivityHandler? =null
    lateinit var binding:WebviewFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val url = WebViewFragmentArgs.fromBundle(arguments!!).url
        val factory = InjectorUtils.providWebViewViewModelFactory(url)
        val webviewViewModel = ViewModelProviders.of(this,factory).get(WebViewViewModel::class.java)

         binding = DataBindingUtil.inflate<WebviewFragmentBinding>(
                inflater, R.layout.webview_fragment, container, false).apply {
            viewModel = webviewViewModel
            setLifecycleOwner(this@WebViewFragment)
        }


        binding.toolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        subscribeUi(webviewViewModel,binding)

        return binding.root
    }



    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context?.let {
            handler = context as IRecipeActivityHandler  //We need activity reference to handle back press event for this fragment
            handler?.setOnBackPressListener(this)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler?.setOnBackPressListener(this) //reset the back press listener to null
    }

    private fun subscribeUi(webviewViewModel: WebViewViewModel, binding: WebviewFragmentBinding) {

        webviewViewModel.displayMessage.observe(viewLifecycleOwner, Observer { displayMessage  ->
            val snackbar = Snackbar.make(
                    binding.root,
                    displayMessage,
                    Snackbar.LENGTH_SHORT
            )
            snackbar.show()
        })
    }


    /**
     * check if web view can traverse back to home page
     * pressing back on homepage will cause navigation to previous fragment
     */
    override fun handleBackPress(): Boolean {
        var canGoBack=binding.newsWebView?.canGoBack()
        if (canGoBack==true) binding.newsWebView?.goBack()
        return canGoBack
    }



}