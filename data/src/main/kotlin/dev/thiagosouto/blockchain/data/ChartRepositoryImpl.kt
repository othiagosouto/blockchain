package dev.thiagosouto.blockchain.data

import dev.thiagosouto.blockchain.domain.Chart
import dev.thiagosouto.blockchain.domain.ChartParameters
import dev.thiagosouto.blockchain.domain.ChartRepository
import dev.thiagosouto.blockchain.domain.ChartRequest
import dev.thiagosouto.blockchain.domain.RemoteSource

internal class ChartRepositoryImpl(private val remoteSource: RemoteSource) : ChartRepository {
    override suspend fun getChart(params: ChartParameters): Chart =
        remoteSource.fetchChartInfo(params.toChartRequest())

    private fun ChartParameters.toChartRequest() = ChartRequest(
        chartId = this.chartId,
        timespan = this.timespan,
        rollingAverage = this.rollingAverage,
        start = this.start,
        format = this.format,
        sampled = this.sampled,
    )
}
