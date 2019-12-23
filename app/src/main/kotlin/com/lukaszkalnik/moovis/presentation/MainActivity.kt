package com.lukaszkalnik.moovis.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lukaszkalnik.moovis.R.layout
import kotlinx.android.synthetic.main.activity_main.main_popular_movies_list

class MainActivity : AppCompatActivity() {

    private val moviesAdapter = MovieTileAdapter { id -> println("Movie id $id clicked") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        main_popular_movies_list.adapter = moviesAdapter
    }
}