package com.kiyosuke.corona_grapher.view

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.kiyosuke.corona_grapher.R
import kotlin.math.max

class CircleFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val drawable: ShapeDrawable by lazy {
        ShapeDrawable(OvalShape()).apply {
            this.paint.color = ContextCompat.getColor(context, R.color.danger)
        }
    }

    var circleColor: Int = ContextCompat.getColor(context, R.color.danger)
        set(value) {
            if (field != value) {
                field = value
                drawable.paint.color = value
                invalidateDrawable(drawable)
            }
        }

    init {
        background = drawable
        invalidateDrawable(drawable)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val r = max(measuredWidth, measuredHeight)
        setMeasuredDimension(r, r)
    }
}