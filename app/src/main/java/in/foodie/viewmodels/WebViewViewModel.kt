package `in`.foodie.viewmodels

import `in`.foodie.FoodieApp
import `in`.foodie.R
import `in`.foodie.database.RecipeRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import android.webkit.WebView
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebViewClient
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import java.util.*


class WebViewViewModel internal constructor(
         val url: String
) : ViewModel()
{

    var hideProgress=ObservableBoolean();
    var displayMessage=MutableLiveData<String>()

    inner class Client : WebViewClient() {
        override fun onReceivedError(view: WebView, request: WebResourceRequest,
                                     error: WebResourceError) {
            super.onReceivedError(view, request, error)
            hideProgress(true)
            Log.i("WebViewViewModel","Loading error")
            postDisplayMessage(FoodieApp.instance.getString(R.string.messgae_network_error))
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            hideProgress(true)
            Log.i("WebViewViewModel","onPageFinished")
        }
    }

    private fun postDisplayMessage(message: String?) = displayMessage.postValue(message)

    fun getWebviewClient() =Client()

    private fun hideProgress(isHide:Boolean) { this.hideProgress.set(isHide)}
}