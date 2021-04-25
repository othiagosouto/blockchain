package dev.thiagosouto.blockchain.features.charts

import dev.thiagosouto.blockchain.domain.Chart
import dev.thiagosouto.blockchain.features.charts.widget.ChartView
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ChartStateHandlerTest {
    private lateinit var interactionHandler: (ChartsInteractions) -> Unit
    private lateinit var chartView: ChartView
    private lateinit var stringProvider: (Int) -> String
    private lateinit var stateMachine: ChartStateHandler
    private val chartId = "market-price"

    @Before
    fun setup() {
        chartView = mockk(relaxed = true)
        stringProvider = mockk(relaxed = true)
        interactionHandler = mockk(relaxed = true)
        stateMachine =
            ChartStateHandler(chartId, stringProvider, interactionHandler, chartView)
    }

    @Test
    fun `should handle Idle State`() {
        stateMachine.handle(ChartsScreenState.Idle)

        verify { interactionHandler(ChartsInteractions.OpenedScreen(chartId)) }
    }

    @Test
    fun `should handle Loading State`() {
        stateMachine.handle(ChartsScreenState.Loading)

        verify { chartView.showLoading() }
    }

    @Test
    fun `should handle Success State`() {
        val chart = Chart(emptyList(), "desc", "name", "unit", chartId)
        stateMachine.handle(ChartsScreenState.Success(chart))

        verify { chartView.showChart(chart) }
    }

    @Test
    fun `should handle Failed State`() {
        val titleRes = 1
        val descriptionRes = 2
        val callbackSlot: CapturingSlot<() -> Unit> = slot()
        every { stringProvider(titleRes) } returns "title"
        every { stringProvider(descriptionRes) } returns "description"
        every { chartView.showError("title", "description", capture(callbackSlot)) } returns Unit

        stateMachine.handle(ChartsScreenState.Failed(1, 2))
        callbackSlot.captured.invoke()

        verify { interactionHandler(ChartsInteractions.ClickedOnRetry(chartId)) }
        verify { chartView.showError("title", "description", any()) }
    }

}
