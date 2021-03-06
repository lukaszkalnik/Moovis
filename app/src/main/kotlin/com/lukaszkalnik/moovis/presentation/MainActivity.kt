package com.lukaszkalnik.moovis.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.lukaszkalnik.moovis.R.layout
import com.lukaszkalnik.moovis.domain.UseCaseInjector
import com.lukaszkalnik.moovis.presentation.model.toMovieTileItems
import com.lukaszkalnik.moovis.runtimeconfiguration.data.AppConfig
import com.lukaszkalnik.moovis.util.VerticalSpaceItemDecoration
import com.lukaszkalnik.moovis.util.viewModel
import kotlinx.android.synthetic.main.activity_main.main_popular_movies_list

class MainActivity : AppCompatActivity() {

    private val moviesAdapter = MovieTileAdapter { id -> println("Movie id $id clicked") }

    private val viewModel: MainViewModel by viewModel {
        MainViewModel(
            UseCaseInjector.getConfiguration,
            UseCaseInjector.getPopularMovies,
            AppConfig.instance,
            ::toMovieTileItems
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        with(main_popular_movies_list) {
            addItemDecoration(VerticalSpaceItemDecoration(16))
            adapter = moviesAdapter
        }

        viewModel.movies.observe(this) { movies ->
            moviesAdapter.submitList(movies)
        }
    }
}
