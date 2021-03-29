package com.dyamschikov.newsapiapp.utils.ui

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dyamschikov.newsapiapp.R

class CustomListDecorator(context: Context, private val isHeader: Boolean) :
    RecyclerView.ItemDecoration() {

    private val divider = ContextCompat.getDrawable(context, R.drawable.divider_custom_list)
    private val margin = context.resources.getDimensionPixelSize(R.dimen.dimen_sx)

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)

        val dividerLeft = parent.paddingLeft + margin
        val dividerRight = parent.width - parent.paddingRight

        val childCount = parent.childCount

        val startIndex = when (isHeader) {
            true -> IS_HEADER
            else -> NO_HEADER
        }

        for (i in startIndex until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom = dividerTop + divider?.intrinsicHeight!!
            divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            divider.draw(canvas)
        }
    }

    companion object {
        private const val IS_HEADER = 1
        private const val NO_HEADER = 0
    }
}