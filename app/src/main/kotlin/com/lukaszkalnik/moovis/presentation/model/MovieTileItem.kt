package com.lukaszkalnik.moovis.presentation.model

data class Id(val idContents: String)

data class MovieTileItem(
    val id: Id,
    val title: String,
    val imageUri: String,
    val description: String
)
