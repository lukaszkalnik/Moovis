package com.lukaszkalnik.moovis.presentation.viewmodel

import com.lukaszkalnik.moovis.data.model.MoviesPage
import com.lukaszkalnik.moovis.data.model.TmdbConfiguration
import com.lukaszkalnik.moovis.presentation.MainViewModel
import com.lukaszkalnik.moovis.presentation.model.MovieTileItem
import com.lukaszkalnik.moovis.runtimeconfiguration.data.AppConfig
import com.lukaszkalnik.moovis.runtimeconfiguration.data.ImageWidth
import com.lukaszkalnik.moovis.util.InstantTaskExecutorExtension
import com.lukaszkalnik.moovis.util.SuspendFun
import com.lukaszkalnik.moovis.util.SuspendFun1
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.*

@ExperimentalCoroutinesApi
@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {

    private val getConfiguration = mockk<SuspendFun<TmdbConfiguration>>()

    private val moviesPage = MoviesPage(
        page = 1,
        results = listOf(
            mockk(),
            mockk()
        ),
        totalResults = 10_000,
        totalPages = 100
    )
    private val getPopularMovies = mockk<SuspendFun1<Int, MoviesPage>> getPopularMovies@{
        coEvery { this@getPopularMovies.invoke(1) } returns moviesPage
    }

    private val appConfig = mockk<AppConfig>(relaxUnitFun = true)

    private val expectedMovieTileItems = listOf(
        MovieTileItem(
            1,
            "My scary movie 1",
            "http://google.de",
            "This is a great movie.",
            1.5f
        ),
        MovieTileItem(
            2,
            "My scary movie 2",
            "http://google.de",
            "This is a great movie as well.",
            1.3f
        )
    )
    private val toMovieTileItems = mockk<(MoviesPage, AppConfig) -> List<MovieTileItem>> toMovieTileItems@{
        every { this@toMovieTileItems.invoke(moviesPage, appConfig) } returns expectedMovieTileItems
    }

    @Test
    fun `when received configuration then store configuration`() {
        val baseUrl = "Some url"
        val posterSizes = listOf("w500", "original")
        val configuration = mockk<TmdbConfiguration> {
            every { images.secureBaseUrl } returns baseUrl
            every { images.posterSizes } returns posterSizes
        }
        coEvery { getConfiguration() } returns configuration

        val viewModel = MainViewModel(
            getConfiguration::invoke,
            getPopularMovies::invoke,
            appConfig,
            toMovieTileItems,
            TestCoroutineScope()
        )
        viewModel.movies.observeForever { movieTileItems ->
            assertThat(movieTileItems).containsExactlyElementsOf(expectedMovieTileItems)
        }

        coVerifySequence {
            getPopularMovies(1)
            getConfiguration()
        }
        verify { appConfig.imagesBaseUrl = baseUrl }
        verify {
            appConfig.posterSizes = listOf(
                ImageWidth("w500"),
                ImageWidth("original")
            )
        }
    }
}
