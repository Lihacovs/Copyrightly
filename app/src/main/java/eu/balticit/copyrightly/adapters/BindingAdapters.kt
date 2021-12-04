package eu.balticit.copyrightly.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import eu.balticit.copyrightly.R

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("profileImageFromUrl")
fun bindProfileImageFromUrl(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(imageUrl)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_copyright_98570))
            .circleCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
}