package `in`.foodie.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
/**
 * View Model Factory for [WebViewViewModel]
 * Using Factory allows us to pass arguments to ViewModel constructor
 */
class WebViewViewModelFactory(
        private val url: String
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = WebViewViewModel(url) as T
}