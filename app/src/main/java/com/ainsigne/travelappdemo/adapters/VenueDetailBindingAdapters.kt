package com.ainsigne.travelappdemo.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * Data Binding adapters specific to [VenueDetailsFragment].
 */

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}

@BindingAdapter("textForDesc")
fun bindTextForDesc(view: TextView, text: String?) {
    if (!text.isNullOrEmpty()) {
        view.text = text
    }
}


@BindingAdapter("isGone")
fun bindIsGone(view: FloatingActionButton, isGone: Boolean?) {
    if (isGone == null || isGone) {
        view.hide()
    } else {
        view.show()
    }
}


//@BindingAdapter("wateringText")
//fun bindWateringText(textView: TextView, wateringInterval: Int) {
//    val resources = textView.context.resources
//    val quantityString = resources.getQuantityString(R.plurals.watering_needs_suffix,
//        wateringInterval, wateringInterval)
//
//    textView.text = SpannableStringBuilder()
//        .bold { append(resources.getString(R.string.watering_needs_prefix)) }
//        .append(" ")
//        .italic { append(quantityString) }
//}