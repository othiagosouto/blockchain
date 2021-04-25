package dev.thiagosouto.blockchain.features.charts

sealed class ChartsInteractions {
    data class OpenedScreen(val chartId: String) : ChartsInteractions()
    data class ClickedOnRetry(val chartId: String) : ChartsInteractions()
}
