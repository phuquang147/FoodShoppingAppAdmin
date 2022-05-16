package xyz.daijoubuteam.foodshoppingappadmin.utils.BindingAdapter

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("imageUrl")
fun setImageUrlDescriptionUri(view: ImageView, url: Uri?) {
    Glide
        .with(view.context)
        .load(url)
        .centerCrop()
        .into(view);
}

@BindingAdapter("imageUrl")
fun setImageUrlAvatarString(view: ImageView, url: String?) {
    val uri: Uri? = if(url.isNullOrBlank()) null else url.toUri()
    setImageUrlDescriptionUri(view, uri)
}