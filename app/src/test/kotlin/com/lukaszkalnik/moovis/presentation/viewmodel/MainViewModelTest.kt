package com.lukaszkalnik.moovis.presentation.viewmodel

import com.lukaszkalnik.moovis.data.model.Configuration
import com.lukaszkalnik.moovis.presentation.MainViewModel
import com.lukaszkalnik.moovis.util.InstantTaskExecutorExtension
import com.lukaszkalnik.moovis.util.SuspendFun
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.*

@ExperimentalCoroutinesApi
@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {

    private val getConfiguration = mockk<SuspendFun<Configuration>>()

    private val viewModel by lazy {
        MainViewModel(
            getConfiguration::invoke,
            TestCoroutineDispatcher()
        )
    }

    @Test
    fun `when received configuration then emit configuration`() {
        val baseUrl = "Some url"
        val configuration = mockk<Configuration> {
            every { images.baseUrl } returns baseUrl
        }
        coEvery { getConfiguration() } returns configuration

        viewModel.configuration.observeForever {
            assertThat(it.images.baseUrl).isEqualTo(baseUrl)
        }
    }
}
