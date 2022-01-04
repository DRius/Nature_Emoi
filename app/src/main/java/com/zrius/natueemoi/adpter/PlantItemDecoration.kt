package com.zrius.natueemoi.adpter

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class PlantItemDecoration :RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.bottom = 20
    }
}