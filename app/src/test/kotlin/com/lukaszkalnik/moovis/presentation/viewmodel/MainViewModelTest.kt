package com.lukaszkalnik.moovis.presentation.viewmodel

import com.lukaszkalnik.moovis.data.model.MoviesPage
import com.lukaszkalnik.moovis.data.model.TmdbConfiguration
import com.lukaszkalnik.moovis.presentation.MainViewModel
import com.lukaszkalnik.moovis.runtimeconfiguration.data.AppConfig
import com.lukaszkalnik.moovis.runtimeconfiguration.data.ImageWidth
import com.lukaszkalnik.moovis.util.InstantTaskExecutorExtension
import com.lukaszkalnik.moovis.util.SuspendFun
import com.lukaszkalnik.moovis.util.SuspendFun1
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.*

@ExperimentalCoroutinesApi
@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {

    private val getConfiguration = mockk<SuspendFun<TmdbConfiguration>>()
    // TODO make it strict again and mock and test the returned data properly
    private val getPopularMovies = mockk<SuspendFun1<Int, MoviesPage>>(relaxed = true)
    private val appConfig = mockk<AppConfig>(relaxUnitFun = true)

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
            TestCoroutineDispatcher()
        )

        verify { appConfig.imagesBaseUrl = baseUrl }
        verify {
            appConfig.posterSizes = listOf(
                ImageWidth("w500"),
                ImageWidth("original")
            )
        }
    }
}
