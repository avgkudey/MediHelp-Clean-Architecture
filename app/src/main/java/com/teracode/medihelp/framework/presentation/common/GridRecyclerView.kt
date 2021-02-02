package com.teracode.medihelp.framework.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.GridLayoutAnimationController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridRecyclerView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    RecyclerView(context, attrs, defStyleAttr) {


    override fun setLayoutManager(layout: LayoutManager?) {
        if (layout is GridLayoutManager) {
            super.setLayoutManager(layout)
        } else {
            throw  ClassCastException("You should only use a GridLayoutManager with GridRecyclerView.")
        }
    }

    override fun attachLayoutAnimationParameters(
        child: View?,
        params: ViewGroup.LayoutParams?,
        index: Int,
        count: Int
    ) {

        if (adapter != null && layoutManager is GridLayoutManager) {
            val animationParams: GridLayoutAnimationController.AnimationParameters =
                params?.layoutAnimationParameters as GridLayoutAnimationController.AnimationParameters


            val columns = (layoutManager as GridLayoutManager).spanCount
            animationParams.count = count
            animationParams.index = index
            animationParams.columnsCount = columns
            animationParams.rowsCount = count / columns

            val invertedIndex = count - 1 - index
            animationParams.column = columns - 1 - (invertedIndex % columns)
            animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns
        } else {
            super.attachLayoutAnimationParameters(child, params, index, count)
        }
    }
}