package com.teracode.medihelp.quizmodule.framework.presentation.quizlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teracode.medihelp.R
import com.teracode.medihelp.framework.presentation.common.gone
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import kotlinx.android.synthetic.main.quiz_list_item.view.*
import java.util.ArrayList

class QuizAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val itemInteraction: ItemInteraction? = null,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val differCallback = object : DiffUtil.ItemCallback<Quiz>() {
        override fun areItemsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return QuizViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.quiz_list_item, parent, false),
            lifecycleOwner = lifecycleOwner,
            itemInteraction = itemInteraction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is QuizViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(quizzes: ArrayList<Quiz>) {
        val commitCallback = Runnable {
            itemInteraction?.restoreListPosition()
        }

        differ.submitList(quizzes, commitCallback)

    }


    class QuizViewHolder(
        itemView: View,
        private val lifecycleOwner: LifecycleOwner,
        private val itemInteraction: ItemInteraction?,
    ) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var quiz: Quiz

        fun bind(item: Quiz) = with(itemView) {


            quiz_item_view_btn.setOnClickListener {
                itemInteraction?.onItemClicked(adapterPosition, quiz)
            }
            quiz = item
            quiz_item_title.text = quiz.name


            var quiz_desc = quiz.description

            if (quiz.description != null && quiz.description?.length!! > 150) {
                quiz_desc =
                    quiz.description?.substring(0, 150) + context.getString(R.string.etc_text)
            }


            quiz_item_description.text = quiz_desc


//           quiz.image?.let {
            Glide.with(itemView.context).load("https://firebase.google.com/images/social.png")
                .placeholder(R.drawable.placeholder_image)
                .into(quiz_item_image)
//           }

        }

    }


    interface ItemInteraction {
        fun onItemClicked(position: Int, item: Quiz)

        fun onItemLongPress(position: Int, item: Quiz)

        fun restoreListPosition()
    }
}