package dev.thiagosouto.blockchain.domain

/**
 * Class with information related to some Chart
 *
 * This class has not logic; it's just a data holder
 * @property[axis] response status
 * @property[description] charts name
 * @param[name] chart unit
 * @see[Axis]
 */
data class Chart(
    val axis: List<Axis>,
    val description: String,
    val name: String,
    val unit: String,
    val id: String
)

/**
 * Class with information related the axis of the chart
 *
 * This class has not logic; it's just a data holder
 * @property[timestamp] is the time as unix timestamp
 * @property[value] chart value for given timestamp
 */
data class Axis(val timestamp: Long, val value: Float)
