package com.teracode.medihelp.framework.presentation.druglist

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class DrugItemTouchHelperCallback
constructor(
    private val itemTouchHelperAdapter: ItemTouchHelperAdapter,
): ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(
            ItemTouchHelper.ACTION_STATE_IDLE,
            ItemTouchHelper.START or ItemTouchHelper.END
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        itemTouchHelperAdapter.onItemSwiped(viewHolder.adapterPosition)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
//        return !noteListInteractionManager.isMultiSelectionStateActive()
    }

}


interface ItemTouchHelperAdapter{

    fun onItemSwiped(position: Int)
}















