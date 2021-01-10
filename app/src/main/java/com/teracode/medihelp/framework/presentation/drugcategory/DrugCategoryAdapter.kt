package com.teracode.medihelp.framework.presentation.drugcategory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.teracode.medihelp.R
import com.teracode.medihelp.business.domain.model.Category
import kotlinx.android.synthetic.main.category_list_item.view.*
import java.util.ArrayList

class DrugCategoryAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val itemInteraction: ItemInteraction? = null,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val differCallback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DrugCategoryViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_list_item, parent, false),
            lifecycleOwner = lifecycleOwner,
            itemInteraction = itemInteraction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DrugCategoryViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(categoryList: ArrayList<Category>) {
        val commitCallback = Runnable {
            itemInteraction?.restoreListPosition()
        }

        differ.submitList(categoryList,commitCallback)

    }


    class DrugCategoryViewHolder(
        itemView: View,
        private val lifecycleOwner: LifecycleOwner,
        private val itemInteraction: ItemInteraction?
    ) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var category: Category

        fun bind(item: Category) = with(itemView) {
            setOnClickListener {
                itemInteraction?.onItemClicked(adapterPosition, category)
            }

            setOnLongClickListener {
                itemInteraction?.onItemLongPress(adapterPosition, category)
                true
            }

            category=item
            category_item_title.text = category.title
        }

    }


    interface ItemInteraction {
        fun onItemClicked(position: Int, item: Category)

        fun onItemLongPress(position: Int, item: Category)

        fun restoreListPosition()
    }
}