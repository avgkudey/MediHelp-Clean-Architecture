package com.teracode.medihelp.quizmodule.framework.presentation.quizlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teracode.medihelp.R
import com.teracode.medihelp.databinding.QuizListItemBinding
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
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
        val binding = QuizListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return QuizViewHolder(
            itemBinding = binding,
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
        private val itemBinding: QuizListItemBinding,
        private val lifecycleOwner: LifecycleOwner,
        private val itemInteraction: ItemInteraction?,
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private lateinit var quiz: Quiz

        fun bind(item: Quiz) = with(itemView) {


            itemBinding.quizItemViewBtn.setOnClickListener {
                itemInteraction?.onItemClicked(adapterPosition, quiz)
            }
            quiz = item
            itemBinding.quizItemTitle.text = quiz.name


            var quizDesc = quiz.description

            if (quiz.description != null && quiz.description?.length!! > 150) {
                quizDesc =
                    quiz.description?.substring(0, 150) + context.getString(R.string.etc_text)
            }


            itemBinding.quizItemDescription.text = quizDesc


//           quiz.image?.let {
            Glide.with(itemView.context).load("https://firebase.google.com/images/social.png")
                .placeholder(R.drawable.placeholder_image)
                .into(itemBinding.quizItemImage)
//           }

        }

    }


    interface ItemInteraction {
        fun onItemClicked(position: Int, item: Quiz)

        fun onItemLongPress(position: Int, item: Quiz)

        fun restoreListPosition()
    }
}