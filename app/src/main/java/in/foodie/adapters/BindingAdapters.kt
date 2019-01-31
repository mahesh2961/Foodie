package `in`.foodie.adapters

import `in`.foodie.R
import android.text.method.LinkMovementMethod
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    val requestOptions = RequestOptions()
    requestOptions.placeholder(R.drawable.placeholder)
    requestOptions.error(R.drawable.image_error)
    requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
                .setDefaultRequestOptions(requestOptions)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}


@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
        view.text = HtmlCompat.fromHtml(description, FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}


@BindingAdapter("setWebViewClient")
fun setWebViewClient(view: WebView, client: WebViewClient) {
    view.webViewClient = client
}

@BindingAdapter("loadUrl")
fun loadUrl(view: WebView, url: String) {
    view.loadUrl(url)
}

