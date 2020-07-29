package com.hooloovoochimico.redditgallery.androidviews

import android.content.Context
import android.util.AttributeSet
import com.squareup.picasso.Picasso


//mi serviva una imageview quadrata
class SquareImageView : androidx.appcompat.widget.AppCompatImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            widthMeasureSpec
        )
    }
}

fun androidx.appcompat.widget.AppCompatImageView.load(url:String) {
    Picasso.get()
        .load(url)
        .error(android.R.drawable.btn_star)
        .into(this)
}