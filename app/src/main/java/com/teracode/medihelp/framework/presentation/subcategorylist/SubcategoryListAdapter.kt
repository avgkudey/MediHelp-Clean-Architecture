package com.teracode.medihelp.framework.presentation.subcategorylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.teracode.medihelp.R
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.framework.presentation.common.capitalizeWords
import com.teracode.medihelp.framework.presentation.common.invisible
import kotlinx.android.synthetic.main.subcategory_list_item.view.*

class SubcategoryListAdapter(
    private val interaction: Interaction? = null,
    private val lifecycleOwner: LifecycleOwner,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val diffCallback = object : DiffUtil.ItemCallback<Subcategory>() {
        override fun areItemsTheSame(oldItem: Subcategory, newItem: Subcategory): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Subcategory, newItem: Subcategory): Boolean {
            return oldItem == newItem
        }

    }


    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SubcategoryViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.subcategory_list_item, parent, false),
            interaction = interaction,
            lifecycleOwner = lifecycleOwner

        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is SubcategoryViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Subcategory>) {
        val commitCallback = Runnable {
            interaction?.restoreListPosition()
        }
        differ.submitList(list, commitCallback)

    }


    class SubcategoryViewHolder(
        itemView: View,
        private val interaction: Interaction?,
        private val lifecycleOwner: LifecycleOwner,
    ) : RecyclerView.ViewHolder(itemView) {
        private lateinit var subcategory: Subcategory
        fun bind(item: Subcategory) = with(itemView) {
            setOnClickListener {
                interaction?.onItemSelected(adapterPosition, subcategory)
            }

            subcategory = item

            setTextValue(subcategory_item_title, item.title)
        }

        private fun setTextValue(textView: TextView?, value: String?) {

            textView?.let { tView ->
                if (value == null || value.isEmpty()) {
                    tView.invisible()
                }
                tView.text = value?.capitalizeWords()
            }
        }

    }


    interface Interaction {

        fun onItemSelected(position: Int, item: Subcategory)

        fun restoreListPosition()

    }
}