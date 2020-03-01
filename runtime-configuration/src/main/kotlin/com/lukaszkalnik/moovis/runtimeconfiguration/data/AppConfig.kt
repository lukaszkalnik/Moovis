package com.lukaszkalnik.moovis.runtimeconfiguration.data

/**
 * App runtime configuration (not persisted) is kept here.
 */
interface AppConfig {

    /**
     * Current device locale for selecting the results language.
     * Should be in form e.g. en-US.
     */
    var language: String

    /**
     * Current device country for filtering country dependent data like release dates.
     * Should be in form e.g. `US`.
     */
    var region: String

    /**
     * Base url for retrieving images.
     * E.g. https://image.tmdb.org/t/p/
     */
    var imagesBaseUrl: String

    /**
     * List of available poster sizes to include after image base url
     * E.g. [w92, w154, w185, w342, w500, w780, original]
     *
     * Include like this: https://image.tmdb.org/t/p/w500/path_to_the_image.jpg
     */
    var posterSizes: List<ImageWidth>
}

object DefaultAppConfig : AppConfig {

    override var language: String = "en-US"

    override var region: String = "US"

    override var imagesBaseUrl: String = ""

    override var posterSizes: List<ImageWidth> = emptyList()
}

/**
 * Holds Tmdb available image widths of the following format:
 * `[w92, w154, w185, w342, w500, w780, original]`
 *
 * [toInt] simplifies searching for closest match.
 */
@Suppress("EXPERIMENTAL_FEATURE_WARNING")
inline class ImageWidth(
    private val width: String
) : Comparable<ImageWidth> {

    fun toInt(): Int = when (width) {
        "original" -> Int.MAX_VALUE
        else -> width.removePrefix("w").toInt()
    }

    override operator fun compareTo(other: ImageWidth): Int = toInt() - other.toInt()

    override fun toString(): String = width
}

/**
 * Finds the smallest [ImageWidth] bigger or equal to the given [width].
 */
fun List<ImageWidth>.findCeiling(width: Int) = filter { it.toInt() >= width }.min()
