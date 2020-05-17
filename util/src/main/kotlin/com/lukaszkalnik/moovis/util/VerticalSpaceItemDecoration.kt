package com.lukaszkalnik.moovis.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.State

/**
 * [RecyclerView.ItemDecoration] adding a vertical space of given [height] in `dp` between items.
 */
class VerticalSpaceItemDecoration(
    private val height: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        parent.adapter?.let { adapter ->
            if (parent.getChildAdapterPosition(view) != adapter.itemCount - 1) outRect.bottom =
                height.dpToPix(parent.context)
        }
    }
}