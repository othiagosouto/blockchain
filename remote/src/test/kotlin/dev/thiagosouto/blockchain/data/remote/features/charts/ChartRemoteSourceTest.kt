package dev.thiagosouto.blockchain.data.remote.features.charts

import com.google.common.truth.Truth.assertThat
import dev.thiagosouto.blockchain.data.remote.RetrofitFactory
import dev.thiagosouto.blockchain.domain.Axis
import dev.thiagosouto.blockchain.domain.Chart
import dev.thiagosouto.blockchain.domain.ChartRequest
import dev.thiagosouto.blockchain.domain.RemoteSource
import dev.thiagosouto.blockchain.domain.exception.UnexpectedErrorException
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalSerializationApi
class ChartRemoteSourceTest {
    private lateinit var server: MockWebServer
    private val expectedResponse: String = "charts/n_transactions.json".readAsText()
    private lateinit var remoteSource: RemoteSource
    private val chartName = "chartName"
    private val timespan = "timespan"
    private val rollingAverage = "rollingAverage"
    private val start = "start"
    private val format = "format"
    private val sampled = true

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()
        val factory = RetrofitFactory()
        val api = factory.create(server.url("").toString()).create(ChartsApi::class.java)
        remoteSource = ChartRemoteSource(api)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `should return chart when the call did work as expected`() = runBlocking {
        server.enqueue(MockResponse().setBody(expectedResponse))

        val chart = remoteSource.fetchChartInfo(
            ChartRequest(
                chartName,
                timespan,
                rollingAverage,
                start,
                format,
                sampled
            )
        )
        assertThat(chart).isEqualTo(
            Chart(
                axis = listOf(
                    Axis(1586563200 * 1000L, 247852.0f),
                    Axis(1586649600 * 1000L, 228529.0f)
                ),
                description = "The number of daily confirmed Bitcoin transactions.",
                name = "Confirmed Transactions Per Day",
                unit = "Transactions",
                id = chartName
            )
        )
    }

    @Test
    fun `should build url using expected parameters`() = runBlocking {
        server.enqueue(MockResponse().setBody(expectedResponse))

        remoteSource.fetchChartInfo(
            ChartRequest(
                chartName,
                timespan,
                rollingAverage,
                start,
                format,
                sampled
            )
        )
        val request = server.takeRequest()
        val expectedUrl =
            "/charts/chartName?timespan=timespan&rollingAverage=rollingAverage&start=start&format=format&sampled=true"
        assertThat(request.path).isEqualTo(expectedUrl)
    }

    @Test(expected = UnexpectedErrorException::class)
    fun `should throw UnexpectedErrorException when HttpException occurs`(): Unit =
        runBlocking {
            server.enqueue(MockResponse().setResponseCode(400).setBody(expectedResponse))

            remoteSource.fetchChartInfo(ChartRequest(chartName))
        }

    private fun String.readAsText() = ClassLoader.getSystemResource(this).readText()
}
