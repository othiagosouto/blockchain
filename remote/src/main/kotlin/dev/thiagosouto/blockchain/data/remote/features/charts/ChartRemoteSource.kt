package dev.thiagosouto.blockchain.data.remote.features.charts

import dev.thiagosouto.blockchain.domain.Axis
import dev.thiagosouto.blockchain.domain.Chart
import dev.thiagosouto.blockchain.domain.ChartRequest
import dev.thiagosouto.blockchain.domain.RemoteSource

internal class ChartRemoteSource(private val api: ChartsApi) : RemoteSource {

    override suspend fun fetchChartInfo(chartRequest: ChartRequest): Chart = api
        .fetchChartInfo(chartRequest.chartId, chartRequest.toQueryMap())
        .toChart(chartRequest.chartId)

    private fun ChartRequest.toQueryMap(): Map<String, String> {
        val arguments: MutableMap<String, String> = linkedMapOf()
        this.timespan?.let { arguments["timespan"] = it }
        this.rollingAverage?.let { arguments["rollingAverage"] = it }
        this.start?.let { arguments["start"] = it }
        this.format?.let { arguments["format"] = it }
        this.sampled?.let { arguments["sampled"] = "$it" }

        return arguments
    }

    private fun ChartResponse.toChart(chartId: String): Chart = Chart(
        axis = this.values.toAxis(),
        description = this.description,
        name = this.name,
        unit = this.unit,
        id = chartId
    )

    private fun List<ItemValue>.toAxis(): List<Axis> {
        val axis = mutableListOf<Axis>()
        for (itemValue in this) {
            axis.add(Axis(itemValue.timestamp.toCurrentTimeMillis(), itemValue.value))
        }
        return axis
    }

    private fun Long.toCurrentTimeMillis(): Long = this * MILLISECONDS

    companion object {
        private const val MILLISECONDS = 1000
    }
}
