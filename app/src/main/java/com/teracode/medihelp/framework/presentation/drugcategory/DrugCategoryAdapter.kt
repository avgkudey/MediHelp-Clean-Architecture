package com.teracode.medihelp.framework.presentation.drugcategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teracode.medihelp.R
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.databinding.CategoryListItemBinding
import com.teracode.medihelp.framework.presentation.common.gone
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
        val binding = CategoryListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )


        return DrugCategoryViewHolder(
            itemBinding = binding,
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

        differ.submitList(categoryList, commitCallback)

    }


    class DrugCategoryViewHolder(
        private val itemBinding: CategoryListItemBinding,
        private val lifecycleOwner: LifecycleOwner,
        private val itemInteraction: ItemInteraction?,
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private lateinit var category: Category

        fun bind(item: Category) = with(itemView) {
            setOnClickListener {
                itemInteraction?.onItemClicked(adapterPosition, category)
            }

            setOnLongClickListener {
                itemInteraction?.onItemLongPress(adapterPosition, category)
                true
            }

            category = item
            itemBinding.categoryItemTitle.text = category.title

//            category_list_item_description.text = category.description


            if (category.subcategoryCount > 0) {
                itemBinding.categoryListItemSubCount.text =
                    "${context.getText(R.string.subcategories_text)}${category.subcategoryCount}"
            } else {
                itemBinding.categoryListItemSubCount.gone()
            }

            if (category.drugCount > 0) {
                itemBinding.categoryListItemDrugCount.text =
                    "${context.getString(R.string.drug_items_text)}${category.drugCount}"


            } else {
                itemBinding.categoryListItemDrugCount.text =
                    context.getString(R.string.drug_items_not_found)
            }
            itemBinding.categoryListItemDescription.text =
                category.description?.substring(0, 70) + context.getString(R.string.etc_text)


//           category.image?.let {
            Glide.with(itemView.context).load("https://firebase.google.com/images/social.png")
                .placeholder(R.drawable.placeholder_image)
                .into(itemBinding.categoryItemImage)
//           }


            itemBinding.categoryListItemViewMoreBtn.gone()
            itemBinding.categoryListItemDrugCount.gone()
            itemBinding.categoryListItemSubCount.gone()
//            category_list_item_description.gone()
        }

    }


    interface ItemInteraction {
        fun onItemClicked(position: Int, item: Category)

        fun onItemLongPress(position: Int, item: Category)

        fun restoreListPosition()
    }
}