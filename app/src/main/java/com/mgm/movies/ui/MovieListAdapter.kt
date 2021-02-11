package com.mgm.movies.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mgm.movies.data.entities.Movie
import com.mgm.movies.databinding.ItemMovieBinding
import com.mgm.movies.databinding.ItemMovieCategoryBinding

class MovieListAdapter(
    val context: Context,
    var movies: List<Movie>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectionCallBack: (id: Int) -> Unit = { }

    private val ITEM_VIEW_TYPE_CATEGORY = -1
    private val ITEM_VIEW_TYPE_MOVIE = -2

    private var dataItems: ArrayList<DataItem>

    init {
        val groupedList = movies.groupBy { it.category }
        dataItems = ArrayList()

        for (i in groupedList.keys) {
            dataItems.add(DataItem.Category(i))
            for (v in groupedList.getValue(i)) {
                dataItems.add(DataItem.MovieItem(v))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_CATEGORY -> Category.from(parent)
            ITEM_VIEW_TYPE_MOVIE -> MovieViewHolder.from(context, parent)
            else -> throw ClassCastException("Unknown itemViewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                val movieItem = dataItems[position] as DataItem.MovieItem
                holder.bind(movieItem.movie)
                holder.itemView.setOnClickListener { selectionCallBack(movieItem.movie.id.toInt()) }
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
            is DataItem.MovieItem -> ITEM_VIEW_TYPE_MOVIE
        }
    }

    class MovieViewHolder(
        private var context: Context,
        private var binding: ItemMovieBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie

//            movie.posterUrl?.let {
//                GlideApp.with(context).load(it.toGlideUrl())
//                    .into(binding.movieImage)
//            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(context: Context, parent: ViewGroup): MovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)

                return MovieViewHolder(context, binding)
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
        data class MovieItem(val movie: Movie) : DataItem() {
            override val id = movie.id
        }

        data class Category(val categoryName: String) : DataItem() {
            override val id = categoryName.hashCode().toLong()
        }

        abstract val id: Long
    }
}
