package com.lukaszkalnik.moovis.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbConfiguration(
    val images: ImagesConfiguration,
    @SerialName("change_keys")
    val changeKeys: List<String>
)

@Serializable
data class ImagesConfiguration(
    @SerialName("base_url")
    val baseUrl: String,
    @SerialName("secure_base_url")
    val secureBaseUrl: String,
    @SerialName("backdrop_sizes")
    val backdropSizes: List<String>,
    @SerialName("logo_sizes")
    val logoSizes: List<String>,
    @SerialName("poster_sizes")
    val posterSizes: List<String>,
    @SerialName("profile_sizes")
    val profileSizes: List<String>,
    @SerialName("still_sizes")
    val stillSizes: List<String>
)
