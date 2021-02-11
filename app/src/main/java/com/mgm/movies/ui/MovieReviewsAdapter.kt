package com.mgm.movies.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mgm.movies.data.entities.Review
import com.mgm.movies.databinding.ItemMovieCategoryBinding
import com.mgm.movies.databinding.ItemReviewBinding

class MovieReviewsAdapter(
    val context: Context,
    var reviews: List<Review>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_VIEW_TYPE_CATEGORY = -1
    private val ITEM_VIEW_TYPE_REVIEW = -2

    private var dataItems: ArrayList<DataItem>

    init {
        val groupedList = reviews.groupBy { it.category }
        dataItems = ArrayList()

        for (i in groupedList.keys) {
            dataItems.add(DataItem.Category(i))
            for (v in groupedList.getValue(i)) {
                dataItems.add(DataItem.ReviewItem(v))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_CATEGORY -> Category.from(parent)
            ITEM_VIEW_TYPE_REVIEW -> ReviewViewHolder.from(context, parent)
            else -> throw ClassCastException("Unknown itemViewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReviewViewHolder -> {
                val reviewItem = dataItems[position] as DataItem.ReviewItem
                holder.bind(reviewItem.review)
            }
            is Category -> {
                val categoryItem = dataItems[position] as DataItem.Category
                holder.bind(categoryItem.categoryName)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataItems[position]) {
            is DataItem.Category -> ITEM_VIEW_TYPE_CATEGORY
            is DataItem.ReviewItem -> ITEM_VIEW_TYPE_REVIEW
        }
    }

    class ReviewViewHolder(
        private var context: Context,
        private var binding: ItemReviewBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            binding.review = review

            binding.executePendingBindings()
        }

        companion object {
            fun from(context: Context, parent: ViewGroup): ReviewViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemReviewBinding.inflate(layoutInflater, parent, false)

                return ReviewViewHolder(context, binding)
            }
        }
    }


    class Category(private var binding: ItemMovieCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryName: String) {
            binding.categoryGroup = categoryName

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): Category {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieCategoryBinding.inflate(layoutInflater, parent, false)

                return Category(binding)
            }
        }
    }

    sealed class DataItem {
        data class ReviewItem(val review: Review) : DataItem() {
            override val id = review.id.hashCode().toLong()
        }

        data class Category(val categoryName: String) : DataItem() {
            override val id = categoryName.hashCode().toLong()
        }

        abstract val id: Long
    }
}
