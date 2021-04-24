package dev.thiagosouto.blockchain.data.remote.features.charts

import dev.thiagosouto.blockchain.domain.ChartRequest
import dev.thiagosouto.blockchain.domain.RemoteSource
import dev.thiagosouto.blockchain.domain.exception.InternetNotAvailableException
import dev.thiagosouto.blockchain.domain.exception.UnexpectedErrorException
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

class RemoteUtilsKtTest  {

    @Test(expected = InternetNotAvailableException::class)
    fun `should throw InternetNotAvailableException when UnknownHostException occurs `(): Unit =
        runBlocking {
            val api: ChartsApi = ApiThrowable(UnknownHostException())
            val remoteSource: RemoteSource = ChartRemoteSource(api)

            remoteSource.fetchChartInfo(ChartRequest("anyParameter"))
        }

    @Test(expected = UnexpectedErrorException::class)
    fun `should throw UnexpectedErrorException when HttpException occurs`(): Unit =
        runBlocking {
            val response: Response<String> = Response.error(
                400,
                "".toResponseBody("application/json".toMediaTypeOrNull())
            )
            val api: ChartsApi = ApiThrowable(HttpException(response))
            val remoteSource: RemoteSource = ChartRemoteSource(api)

            remoteSource.fetchChartInfo(ChartRequest("anyParameter"))
        }

    @Test(expected = UnexpectedErrorException::class)
    fun `should throw UnexpectedErrorException when IOException occurs`(): Unit =
        runBlocking {
            val api: ChartsApi = ApiThrowable(IOException())
            val remoteSource: RemoteSource = ChartRemoteSource(api)

            remoteSource.fetchChartInfo(ChartRequest("anyParameter"))
        }
}

internal class ApiThrowable(private val throwable: Throwable) : ChartsApi {
    override suspend fun fetchChartInfo(
        chartId: String,
        arguments: Map<String, String>
    ): ChartResponse {
        throw throwable
    }
}
