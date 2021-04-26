package dev.thiagosouto.blockchain.features.charts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.thiagosouto.blockchain.domain.ChartParameters
import dev.thiagosouto.blockchain.domain.ChartRepository
import dev.thiagosouto.blockchain.domain.exception.InternetNotAvailableException
import dev.thiagosouto.blockchain.domain.exception.UnexpectedErrorException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

internal class ChartsViewModel(private val repository: ChartRepository) : ViewModel() {
    private val interactions = Channel<ChartsInteractions>(Channel.UNLIMITED)
    private val _states: MutableStateFlow<ChartsScreenState> =
        MutableStateFlow(ChartsScreenState.Idle)

    init {
        viewModelScope.launch {
            interactions.consumeAsFlow().collect {
                when (it) {
                    is ChartsInteractions.OpenedScreen -> showChart(it.chartId)
                    is ChartsInteractions.ClickedOnRetry -> showChart(it.chartId)
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

    @Suppress("SwallowedException")
    private fun showChart(chartId: String) {
        viewModelScope.launch {
            _states.value = ChartsScreenState.Loading
            try {
                _states.value = ChartsScreenState.Success(
                    repository.getChart(
                        ChartParameters(
                            chartId = chartId,
                            timespan = TIME_SPAN
                        )
                    )
                )
            } catch (e: InternetNotAvailableException) {
                _states.value = ChartsScreenState.Failed(
                    R.string.feature_charts_error_error_title,
                    R.string.feature_charts_error_internet_message,
                )
            } catch (e: UnexpectedErrorException) {
                _states.value = ChartsScreenState.Failed(
                    R.string.feature_charts_error_error_title,
                    R.string.feature_charts_error_default_message,
                )
            }
        }
    }

    companion object {
        private const val TIME_SPAN = "5weeks"
    }
}
