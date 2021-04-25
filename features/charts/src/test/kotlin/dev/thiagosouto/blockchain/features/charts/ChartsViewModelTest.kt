package dev.thiagosouto.blockchain.features.charts

import android.util.Log
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.thiagosouto.blockchain.domain.Chart
import dev.thiagosouto.blockchain.domain.ChartParameters
import dev.thiagosouto.blockchain.domain.ChartRepository
import dev.thiagosouto.blockchain.domain.exception.InternetNotAvailableException
import dev.thiagosouto.blockchain.domain.exception.UnexpectedErrorException
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
    private val chartId = "market-price"

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
                Chart(emptyList(), "description", "market-price", "USD", chartId)

            coEvery {
                repository.getChart(
                    ChartParameters(
                        chartId = chartId,
                        timespan = "5weeks"
                    )
                )
            } returns chart

            val viewModel = ChartsViewModel(repository)

            viewModel.run {
                bind().test {
                    interact(ChartsInteractions.OpenedScreen(chartId))
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

    @Test
    fun `should go to failed state when InternetNotAvailableException occurs`() =
        runBlocking {
            checkErrorStateFrom(
                InternetNotAvailableException(Exception()),
                R.string.feature_charts_error_internet_message,
            )
        }

    @Test
    fun `should go to failed state when UnexpectedErrorException occurs`() =
        runBlocking {
            checkErrorStateFrom(
                UnexpectedErrorException(Exception()),
                R.string.feature_charts_error_default_message,
            )
        }

    @Test
    fun `should retry interaction work as expected`() = runBlocking {
        retryHandling(
            UnexpectedErrorException(Exception()),
            R.string.feature_charts_error_default_message,
        )
    }

    private suspend fun retryHandling(exception: Exception, descriptionResId: Int) {
        val repository: ChartRepository = mockk()
        val chart = Chart(emptyList(), "description", "market-price", "USD", chartId)
        coEvery { repository.getChart(any()) } throws exception andThen chart

        val viewModel = ChartsViewModel(repository)

        viewModel.run {
            bind().test {
                interact(ChartsInteractions.OpenedScreen(chartId))
                interact(ChartsInteractions.ClickedOnRetry(chartId))

                val emissions =
                    listOf(expectItem(), expectItem(), expectItem(), expectItem(), expectItem())
                val viewStates = listOf(
                    ChartsScreenState.Idle,
                    ChartsScreenState.Loading,
                    ChartsScreenState.Failed(
                        R.string.feature_charts_error_error_title,
                        descriptionResId
                    ),
                    ChartsScreenState.Loading,
                    ChartsScreenState.Success(chart)
                )

                assertThat(emissions).isEqualTo(viewStates)
            }
        }
    }

    private suspend fun checkErrorStateFrom(exception: Exception, descriptionResId: Int) {
        val repository: ChartRepository = mockk()
        coEvery { repository.getChart(any()) } throws exception

        val viewModel = ChartsViewModel(repository)

        viewModel.run {
            bind().test {
                interact(ChartsInteractions.OpenedScreen(chartId))
                val emissions = listOf(expectItem(), expectItem(), expectItem())
                val viewStates = listOf(
                    ChartsScreenState.Idle,
                    ChartsScreenState.Loading,
                    ChartsScreenState.Failed(
                        R.string.feature_charts_error_error_title,
                        descriptionResId
                    )
                )

                assertThat(emissions).isEqualTo(viewStates)
            }
        }
    }
}
