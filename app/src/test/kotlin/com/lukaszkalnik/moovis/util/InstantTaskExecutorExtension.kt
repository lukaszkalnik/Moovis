package com.lukaszkalnik.moovis.util

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.*

class InstantTaskExecutorExtension : BeforeEachCallback, AfterEachCallback {

    @ExperimentalCoroutinesApi
    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(TestCoroutineDispatcher())

        ArchTaskExecutor.getInstance()
            .setDelegate(object : TaskExecutor() {

                override fun executeOnDiskIO(runnable: Runnable) = runnable.run()

                override fun postToMainThread(runnable: Runnable) = runnable.run()

                override fun isMainThread() = true
            })
    }

    @ExperimentalCoroutinesApi
    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()

        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}