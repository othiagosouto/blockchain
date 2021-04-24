package dev.thiagosouto.blockchain.domain

/**
 * Interface accountable to access sources of Chart data
 */
interface ChartRepository {
    /**
     * returns chart from data source
     *
     * @param[params] infos necessary to retrieve chart for any given data source
     *
     * @return Chart from data source
     */
    suspend fun getChart(params: ChartParameters): Chart
}

/**
 * A class to holder chart properties
 *
 * @property[chartId] charts id
 * @property[timespan] Duration of the chart, default is 1 year for most charts, 1 week for mempool charts. (Optional)
 * @property[rollingAverage] Duration over which the data should be averaged. (Optional)
 * @property[start] Datetime at which to start the chart. (Optional)
 * @property[format] Either JSON or CSV, defaults to JSON. (Optional)
 * @property[sampled] Boolean set to 'true' or 'false' (default 'true'). If true, limits the
 * number of datapoints returned to ~1.5k for performance reasons. (Optional)
 */
data class ChartParameters(
    val chartId: String,
    val timespan: String? = null,
    val rollingAverage: String? = null,
    val start: String? = null,
    val format: String? = null,
    val sampled: Boolean? = null
)
