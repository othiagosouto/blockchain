package dev.thiagosouto.blockchain.features.charts

internal class ChartsProperties(
    private val map: Map<String, String> = mapOf(
        "market-price" to "Market price",
        "n-transactions" to "Confirmed Transactions",
        "total-bitcoins" to "Bitcoins in circulation"
    )
) {

    val ids: List<String>
        get() = map.keys.toList()

    val titles: List<String>
        get() = map.values.toList()
}
