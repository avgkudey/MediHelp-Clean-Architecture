package com.teracode.medihelp.framework.presentation.drugdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.databinding.DrugDetailItemBinding
import com.teracode.medihelp.framework.presentation.common.capitalizeWords
import com.teracode.medihelp.framework.presentation.common.gone
import com.teracode.medihelp.framework.presentation.common.spliceToLines
import com.teracode.medihelp.util.SymbolConstants.Companion.COMMON_BULLET_POINT
import com.teracode.medihelp.util.SymbolConstants.Companion.COMMON_LINE_SEPARATOR


class DrugDetailAdapter(
    private val interaction: Interaction? = null,
    private val lifecycleOwner: LifecycleOwner,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val callback = object : DiffUtil.ItemCallback<DrugDetail>() {
        override fun areItemsTheSame(oldItem: DrugDetail, newItem: DrugDetail): Boolean {
            return oldItem.title == newItem.title

        }

        override fun areContentsTheSame(oldItem: DrugDetail, newItem: DrugDetail): Boolean {
            return oldItem == newItem
        }

    }


    private val differ = AsyncListDiffer(this, callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = DrugDetailItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DrugDetailViewHolder(
            itemBinding = binding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DrugDetailViewHolder -> {
                val item = differ.currentList.get(position)

//                if (item.cardType == CardType.TEXT) {
//                    if (item.text != null && !item.text.isNullOrEmpty()) {
//                        holder.bind(item)
//                    }
//                } else {
                holder.bind(item)
//                }


            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    fun submitList(item: Drug) {
        val commitCallback = Runnable {
            interaction?.restoreListPosition()
        }


        val list = convertToArray(item)
        differ.submitList(list, commitCallback)


    }

    private fun convertToArray(drug: Drug): ArrayList<DrugDetail> {
        val list: ArrayList<DrugDetail> = ArrayList()


        list.add(
            DrugDetail(
                title = "Title",
                text = drug.title,
                cardType = CardType.TITLE
            )
        )
        list.add(
            DrugDetail(
                title = "Trade Name",
                text = drug.trade_name?.spliceToLines(
                    delimiter = COMMON_LINE_SEPARATOR,
                    bullet = COMMON_BULLET_POINT,
                    sort = true
                )
            )
        )
        list.add(
            DrugDetail(
                title = "Video",
                text = drug.video,
                cardType = CardType.VIDEO
            )
        )
        list.add(
            DrugDetail(
                title = "Pharmacological Name",
                text = drug.pharmacological_name
            )
        )

        list.add(
            DrugDetail(
                title = "Description",
                text = "description"
            )
        )
        list.add(
            DrugDetail(
                title = "Cautions",
                text = drug.cautions?.spliceToLines(COMMON_LINE_SEPARATOR, COMMON_BULLET_POINT)
            )
        )
        list.add(
            DrugDetail(
                title = "Side Effects",
                text = drug.side_effects?.spliceToLines(COMMON_LINE_SEPARATOR, COMMON_BULLET_POINT)
            )
        )
        list.add(
            DrugDetail(
                title = "Dosages",
                text = drug.dosages?.spliceToLines(COMMON_LINE_SEPARATOR, COMMON_BULLET_POINT)
            )
        )
        list.add(
            DrugDetail(
                title = "Maximum Dose",
                text = drug.maximum_dose?.spliceToLines(COMMON_LINE_SEPARATOR, COMMON_BULLET_POINT)
            )
        )
        list.add(
            DrugDetail(
                title = "Antidote",
                text = drug.antidote?.spliceToLines(COMMON_LINE_SEPARATOR, COMMON_BULLET_POINT)
            )
        )
        list.add(
            DrugDetail(
                title = "Indications",
                text = drug.indications?.spliceToLines(COMMON_LINE_SEPARATOR, COMMON_BULLET_POINT)
            )
        )
        list.add(
            DrugDetail(
                title = "Contraindication",
                text = drug.contraindication?.spliceToLines(
                    COMMON_LINE_SEPARATOR,
                    COMMON_BULLET_POINT
                )
            )
        )
        list.add(
            DrugDetail(
                title = "Nursing Implications",
                text = drug.nursing_implications?.spliceToLines(
                    COMMON_LINE_SEPARATOR,
                    COMMON_BULLET_POINT
                )
            )
        )
        list.add(
            DrugDetail(
                title = "Note",
                text = drug.notes?.spliceToLines(COMMON_LINE_SEPARATOR, COMMON_BULLET_POINT)
            )
        )

        return list
    }


    class DrugDetailViewHolder(
        private val itemBinding: DrugDetailItemBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private lateinit var drugDetailItem: DrugDetail

        fun bind(item: DrugDetail) = with(itemView) {

            drugDetailItem = item

//            setTextValue(drug_detail_item_title, item.title)
//            setTextValue(drug_detail_item_value, item.text)

            when (item.cardType) {
                CardType.TEXT -> {

                    if (item.text != null && !item.text.isNullOrEmpty()) {
                        setTextValue(itemBinding.drugDetailItemTitle, item.title)
                        setTextValue(itemBinding.drugDetailItemValue, item.text)
                    } else {
                        itemBinding.drugDetailItemTitle.gone()
                        itemBinding.drugDetailItemValue.gone()
                    }


                }
                CardType.VIDEO -> {
                    setTextValue(itemBinding.drugDetailItemTitle, null)
                    setTextValue(itemBinding.drugDetailItemValue, null)
//                    item.text?.let { videoId ->
//                        youtube_player_view.addYouTubePlayerListener(object :
//                            AbstractYouTubePlayerListener() {
//                            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
//                                youTubePlayer.cueVideo(videoId, 0f)
//
//                                youtube_player_view.visible()
//                            }
//
//                        })
//                    }


                }
                CardType.IMAGE -> {


                }
                CardType.TITLE -> {
                    setTextValue(itemBinding.drugDetailItemTitle, item.text)
                    setTextValue(itemBinding.drugDetailItemValue, null)
                }
                CardType.URL -> {


                }
                CardType.AD -> {


                }
            }
        }


        private fun setTextValue(textView: TextView?, value: String?, changeCase: Boolean = false) {

            textView?.let { tView ->
                if (value == null || value.isEmpty()) {
                    tView.gone()
                }

                if (changeCase) {
                    tView.text = value?.capitalizeWords()

                } else {
                    tView.text = value

                }
            }
        }
    }


    data class DrugDetail(
        var title: String? = null,
        var text: String? = null,
        var cardType: CardType = CardType.TEXT,
    ) {


        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as DrugDetail

            if (title != other.title) return false
            if (text != other.text) return false
            if (cardType != other.cardType) return false
            return true
        }

        override fun hashCode(): Int {
            var result = title.hashCode()
            result = 31 * result + text.hashCode()
            result = 31 * result + cardType.hashCode()
            return result
        }
    }


    interface Interaction {
        fun onItemSelected(position: Int, item: Drug)

        fun restoreListPosition()
    }


    enum class CardType {
        TEXT, VIDEO, IMAGE, TITLE, URL, AD
    }
}