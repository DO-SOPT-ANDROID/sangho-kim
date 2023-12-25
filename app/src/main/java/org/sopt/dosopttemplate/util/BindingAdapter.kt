package org.sopt.dosopttemplate.util

import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("text_with_bottle")
    fun setText(view: TextView, text: String){
        view.text = text + "병"
    }
}