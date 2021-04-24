package dev.thiagosouto.blockchain.domain

/**
 * This interface allow a contract to retrieve chart for any remote source
 */
interface RemoteSource {

    /**
     * This method return object type [Chart]
     *
     * @param[chartRequest] is the info that will be used to create the request to ChartsApi
     *
     * @return the response as type @see[Chart]
     */
    suspend fun fetchChartInfo(chartRequest: ChartRequest): Chart
}

/**
 * A class to holder chart request parameters
 *
 * @property[chartId] target of the charts api
 * @property[timespan]  Duration of the chart, default is 1 year for most charts, 1 week for mempool charts. (Optional)
 * @property[rollingAverage]  Duration over which the data should be averaged. (Optional)
 * @property[start] Datetime at which to start the chart. (Optional)
 * @property[format] Either JSON or CSV, defaults to JSON. (Optional)
 * @property[sampled] Boolean set to 'true' or 'false' (default 'true'). If true, limits the
 * number of datapoints returned to ~1.5k for performance reasons. (Optional)
 */
data class ChartRequest(
    val chartId: String,
    val timespan: String? = null,
    val rollingAverage: String? = null,
    val start: String? = null,
    val format: String? = null,
    val sampled: Boolean? = null
)
