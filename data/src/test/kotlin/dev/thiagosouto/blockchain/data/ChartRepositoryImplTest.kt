package dev.thiagosouto.blockchain.data

import com.google.common.truth.Truth.assertThat
import dev.thiagosouto.blockchain.domain.Chart
import dev.thiagosouto.blockchain.domain.ChartParameters
import dev.thiagosouto.blockchain.domain.ChartRepository
import dev.thiagosouto.blockchain.domain.ChartRequest
import dev.thiagosouto.blockchain.domain.RemoteSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

internal class ChartRepositoryImplTest {

    @Test
    fun `should return Chart from remote source`() = runBlocking {
        val chart = Chart(emptyList(), "description", "chart", "USD", "market-price")
        val remoteSource: RemoteSource = mockk()
        coEvery { remoteSource.fetchChartInfo(ChartRequest("chart")) } returns chart
        val chartRepository: ChartRepository = ChartRepositoryImpl(remoteSource)
        val chartParameters = ChartParameters("chart")

        assertThat(chartRepository.getChart(chartParameters)).isEqualTo(chart)
    }
}
