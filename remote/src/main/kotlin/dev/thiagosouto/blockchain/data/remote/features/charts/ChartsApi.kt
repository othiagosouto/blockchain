package dev.thiagosouto.blockchain.data.remote.features.charts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * An API to access [ChartsApi](https://www.blockchain.com/api/charts_api) from BlockChain
 */
internal interface ChartsApi {

    /**
     * A method to access retrieve some chart given @param chartName
     *
     * @param[chartId] is the desired chart identifier
     * @param[timespan]  Duration of the chart, default is 1 year for most charts, 1 week for mempool charts. (Optional)
     * @param[rollingAverage]  Duration over which the data should be averaged. (Optional)
     * @param[start] Datetime at which to start the chart. (Optional)
     * @param[format] Either JSON or CSV, defaults to JSON. (Optional)
     * @param[sampled] Boolean set to 'true' or 'false' (default 'true'). If true, limits the
     * number of datapoints returned to ~1.5k for performance reasons. (Optional)
     *
     * @return the response deserialized as type @see[ChartResponse]
     */
    @GET("charts/{chartId}")
    suspend fun fetchChartInfo(
        @Path("chartId") chartId: String,
        @QueryMap arguments: Map<String, String>
    ): ChartResponse
}

/**
 * Response deserialized from charts api
 *
 * @property[status] response status
 * @property[name] charts name
 * @property[unit] chart unit
 * @property[period] chart period
 * @property[description] chart descriptions
 * @property[values] are the axis x and y from the chart
 * @see[ItemValue]
 */
@Serializable
data class ChartResponse(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    val values: List<ItemValue>
)

/**
 * class holding pair of data
 *
 * @property[timestamp] Unix timestamp (2015-09-18T00:00:00+00:00)
 * @property[value] value for given chart name
 *
 */
@Serializable
data class ItemValue(
    @SerialName("x") val timestamp: Long,
    @SerialName("y") val value: Float
)
