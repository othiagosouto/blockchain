package dev.thiagosouto.blockchain.features.charts

sealed class ChartsInteractions {
    class OpenedScreen(val chartId: String) : ChartsInteractions()
    class ClickedOnRetry(val chartId: String) : ChartsInteractions()
}
