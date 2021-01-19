package com.teracode.medihelp.framework.presentation.druglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.teracode.medihelp.R
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.framework.presentation.common.capitalizeWords
import com.teracode.medihelp.framework.presentation.common.gone
import com.teracode.medihelp.framework.presentation.common.invisible
import kotlinx.android.synthetic.main.drug_list_item.view.*

class DrugListAdapter(
    private val interaction: Interaction? = null,
    private val lifecycleOwner: LifecycleOwner,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Drug>() {
        override fun areItemsTheSame(oldItem: Drug, newItem: Drug): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Drug, newItem: Drug): Boolean {
            return oldItem == newItem
        }

    }


    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DrugViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.drug_list_item, parent, false),
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
        itemView: View,
        private val interaction: Interaction?,
        private val lifecycleOwner: LifecycleOwner,
    ) : RecyclerView.ViewHolder(itemView) {
        private lateinit var drug: Drug
        fun bind(item: Drug) = with(itemView) {
            setOnClickListener {
                interaction?.onItemSelected(adapterPosition, drug)
            }

            drug = item

            setTextValue(drug_item_title, item.title)
            setTradeName(item.trade_name, drug_list_item_trade_name)

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