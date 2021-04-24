package dev.thiagosouto.blockchain.features.charts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.thiagosouto.blockchain.domain.ChartParameters
import dev.thiagosouto.blockchain.domain.ChartRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class ChartsViewModel(private val repository: ChartRepository) : ViewModel() {
    private val interactions = Channel<ChartsInteractions>(Channel.UNLIMITED)
    private val _states: MutableStateFlow<ChartsScreenState> =
        MutableStateFlow(ChartsScreenState.Idle)

    init {
        viewModelScope.launch {
            interactions.consumeAsFlow().collect {
                when (it) {
                    is ChartsInteractions.OpenedScreen -> showChart()
                }
            }
        }
    }

    fun bind() = _states.asStateFlow()

    fun interact(interaction: ChartsInteractions) {
        viewModelScope.launch {
            interactions.send(interaction)
        }
    }

    private fun showChart() {
        viewModelScope.launch {
            _states.value = ChartsScreenState.Loading
            _states.value = ChartsScreenState.Success(
                repository.getChart(
                    ChartParameters(
                        chartId = MARKET_PRICE,
                        timespan = TIME_SPAN
                    )
                )
            )
        }
    }

    companion object {
        private const val MARKET_PRICE = "market-price"
        private const val TIME_SPAN = "5weeks"
    }
}
