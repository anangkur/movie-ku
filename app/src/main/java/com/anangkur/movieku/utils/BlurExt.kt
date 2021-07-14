package com.anangkur.movieku.utils

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur

fun BlurView.blur(
    viewToBlur: ViewGroup = this,
    radius: Float = 16f,
    isAutoUpdate: Boolean = true,
    hasFixedTransformationMatrix: Boolean = true,
) {
    this.setupWith(viewToBlur)
        .setBlurAlgorithm(RenderScriptBlur(this.context))
        .setBlurRadius(radius)
        .setBlurAutoUpdate(isAutoUpdate)
        .setHasFixedTransformationMatrix(hasFixedTransformationMatrix)
}

fun BlurView.blur(
    viewToBlur: ViewGroup = this,
    drawableToBlur: Drawable,
    radius: Float = 16f,
    isAutoUpdate: Boolean = true,
    hasFixedTransformationMatrix: Boolean = true,
) {
    this.setupWith(viewToBlur)
        .setFrameClearDrawable(drawableToBlur)
        .setBlurAlgorithm(RenderScriptBlur(this.context))
        .setBlurRadius(radius)
        .setBlurAutoUpdate(isAutoUpdate)
        .setHasFixedTransformationMatrix(hasFixedTransformationMatrix)
}