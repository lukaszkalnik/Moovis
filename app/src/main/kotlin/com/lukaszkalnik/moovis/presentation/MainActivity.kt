package com.lukaszkalnik.moovis.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.lukaszkalnik.moovis.R.layout
import com.lukaszkalnik.moovis.domain.UseCaseInjector
import com.lukaszkalnik.moovis.presentation.model.toMovieTileItems
import com.lukaszkalnik.moovis.runtimeconfiguration.data.AppConfig
import kotlinx.android.synthetic.main.activity_main.main_popular_movies_list

class MainActivity : AppCompatActivity() {

    private val moviesAdapter = MovieTileAdapter { id -> println("Movie id $id clicked") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        main_popular_movies_list.adapter = moviesAdapter

        val viewModel = ViewModelProvider(
            this,
            MainViewModel.Factory(
                UseCaseInjector.getConfiguration,
                UseCaseInjector.getPopularMovies,
                AppConfig.instance,
                ::toMovieTileItems
            )
        )[MainViewModel::class.java]

        viewModel.movies.observe(this) { movies ->
            moviesAdapter.submitList(movies)
        }
    }
}
