package com.lukaszkalnik.moovis.presentation

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.lukaszkalnik.moovis.R
import com.lukaszkalnik.moovis.R.layout
import com.lukaszkalnik.moovis.presentation.MovieTileAdapter.ViewHolder
import com.lukaszkalnik.moovis.presentation.model.MovieTileItem
import com.lukaszkalnik.moovis.util.dpToPix
import kotlinx.android.synthetic.main.item_view_movie_tile.view.movie_tile_description
import kotlinx.android.synthetic.main.item_view_movie_tile.view.movie_tile_popularity
import kotlinx.android.synthetic.main.item_view_movie_tile.view.movie_tile_title

class MovieTileAdapter(
    private val onMovieClicked: (Int) -> Unit
) : ListAdapter<MovieTileItem, ViewHolder>(DefaultDiffCallback<MovieTileItem>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(layout.item_view_movie_tile, parent, false)
            .let { ViewHolder(it) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { movieTile ->
            with(holder) {
                itemView.setOnClickListener { onMovieClicked(movieTile.id) }
                title.text = movieTile.title

                Glide.with(itemView).asDrawable().load(movieTile.imageUri).override(350).into(
                    object : CustomTarget<Drawable>() {
                        override fun onLoadCleared(drawable: Drawable?) {}

                        override fun onResourceReady(drawable: Drawable, transition: Transition<in Drawable>?) {
                            description.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                        }
                    }
                )

                with(description) {
                    compoundDrawablePadding = 8.dpToPix(itemView.context)
                    text = movieTile.description
                }

                popularity.text = itemView.context.getString(R.string.movie_tile_popularity, movieTile.popularity)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.movie_tile_title
        val description: TextView = itemView.movie_tile_description
        val popularity: TextView = itemView.movie_tile_popularity
    }
}

class DefaultDiffCallback<T> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem === newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}
