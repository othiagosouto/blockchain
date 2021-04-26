package dev.thiagosouto.blockchain.features.charts

import dev.thiagosouto.blockchain.features.charts.widget.ChartView

internal class ChartStateHandler(
    private val chartId: String,
    private val stringProvider: (Int) -> String,
    private val interactionHandler: (ChartsInteractions) -> Unit,
    private val chartView: ChartView
) {

    fun handle(state: ChartsScreenState) {
        when (state) {
            is ChartsScreenState.Idle -> interactionHandler(ChartsInteractions.OpenedScreen(chartId))
            is ChartsScreenState.Loading -> chartView.showLoading()
            is ChartsScreenState.Success -> chartView.showChart(state.chart)
            is ChartsScreenState.Failed -> chartView.showError(
                stringProvider(state.titleResId),
                stringProvider(state.descriptionResId)
            ) { interactionHandler(ChartsInteractions.ClickedOnRetry(chartId)) }
        }
    }
}
