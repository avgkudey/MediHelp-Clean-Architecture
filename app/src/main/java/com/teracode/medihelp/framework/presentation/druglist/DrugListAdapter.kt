package com.teracode.medihelp.framework.presentation.druglist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.databinding.DrugListItemBinding
import com.teracode.medihelp.framework.presentation.common.capitalizeWords
import com.teracode.medihelp.framework.presentation.common.gone
import com.teracode.medihelp.framework.presentation.common.invisible

class DrugListAdapter(
    private val interaction: Interaction? = null,
    private val lifecycleOwner: LifecycleOwner,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val callback = object : DiffUtil.ItemCallback<Drug>() {
        override fun areItemsTheSame(oldItem: Drug, newItem: Drug): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Drug, newItem: Drug): Boolean {
            return oldItem == newItem
        }

    }


    private val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = DrugListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DrugViewHolder(
            itemBinding = binding,
            interaction = interaction,
            lifecycleOwner = lifecycleOwner

        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is DrugViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Drug>) {
        val commitCallback = Runnable {
            interaction?.restoreListPosition()
        }
        differ.submitList(list, commitCallback)

    }


    class DrugViewHolder(
        private val  itemBinding:  DrugListItemBinding,
        private val interaction: Interaction?,
        private val lifecycleOwner: LifecycleOwner,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        private lateinit var drug: Drug
        fun bind(item: Drug) = with(itemView) {
            setOnClickListener {
                interaction?.onItemSelected(adapterPosition, drug)
            }

            drug = item

            setTextValue(itemBinding.drugItemTitle, item.title)
            setTradeName(item.trade_name, itemBinding.drugListItemTradeName)

        }

        private fun setTextValue(textView: TextView?, value: String?) {

            textView?.let { tView ->
                if (value == null || value.isEmpty()) {
                    tView.invisible()
                }
                tView.text = value?.capitalizeWords()
            }
        }

        private fun setTradeName(tradeName: String?, textView: TextView) {
            textView.gone()
            if (tradeName == null) {
                textView.gone()
                return
            }
//
//            val trade_text_arr = tradeName.split("~")
//
//            for (tn in trade_text_arr) {
//                var exValue = textView.text.toString()
//                var newVal = exValue + "\n" + tn
//                setTextValue(textView, newVal)
//            }


        }

    }


    interface Interaction {

        fun onItemSelected(position: Int, item: Drug)

        fun restoreListPosition()

        fun isMultiSelectionModeEnabled(): Boolean

        fun activateMultiSelectionMode()

        fun isNoteSelected(note: Drug): Boolean
    }
}