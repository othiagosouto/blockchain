package dev.thiagosouto.blockchain.features.charts

import android.util.Log
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.thiagosouto.blockchain.domain.Chart
import dev.thiagosouto.blockchain.domain.ChartParameters
import dev.thiagosouto.blockchain.domain.ChartRepository
import io.mockk.coEvery
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class ChartsViewModelTest {
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Log::class)
        justRun { Log.e(any(), any(), any()) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `should init with expected state`() = runBlocking {
        val viewModel = ChartsViewModel(mockk())

        assertThat(viewModel.bind().value).isEqualTo(ChartsScreenState.Idle)
    }

    @Test
    fun `when loaded correct then should move to state success state with expected chart`() =
        runBlocking {
            val repository: ChartRepository = mockk()
            val chart =
                Chart(emptyList(), "description", "market-price", "USD", "market-price")

            coEvery {
                repository.getChart(
                    ChartParameters(
                        chartId = "market-price",
                        timespan = "5weeks"
                    )
                )
            } returns chart

            val viewModel = ChartsViewModel(repository)

            viewModel.run {
                bind().test {
                    interact(ChartsInteractions.OpenedScreen)
                    val emissions = listOf(expectItem(), expectItem(), expectItem())
                    val viewStates = listOf(
                        ChartsScreenState.Idle,
                        ChartsScreenState.Loading,
                        ChartsScreenState.Success(chart)
                    )

                    assertThat(emissions).isEqualTo(viewStates)
                }
            }
        }
}
