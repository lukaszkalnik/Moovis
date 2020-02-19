package com.lukaszkalnik.moovis.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lukaszkalnik.moovis.R.layout
import com.lukaszkalnik.moovis.presentation.MovieTileAdapter.ViewHolder
import com.lukaszkalnik.moovis.presentation.model.MovieTileItem
import kotlinx.android.synthetic.main.item_view_movie_tile.view.movie_tile_description
import kotlinx.android.synthetic.main.item_view_movie_tile.view.movie_tile_image
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
                Glide.with(holder.itemView).load(movieTile.imageUri).override(200).into(image)
                description.text = movieTile.description
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.movie_tile_title
        val image = itemView.movie_tile_image
        val description = itemView.movie_tile_description
    }
}

class DefaultDiffCallback<T> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem === newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}
