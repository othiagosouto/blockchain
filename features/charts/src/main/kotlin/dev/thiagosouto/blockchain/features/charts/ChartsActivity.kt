package dev.thiagosouto.blockchain.features.charts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import dev.thiagosouto.blockchain.features.charts.databinding.FeaturesChartsActivityChartsBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChartsActivity : ComponentActivity() {
    private val chartsViewModel: ChartsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = FeaturesChartsActivityChartsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        lifecycleScope.launchWhenCreated {
            chartsViewModel.bind().collect { state ->
                when (state) {
                    is ChartsScreenState.Idle -> chartsViewModel.interact(ChartsInteractions.OpenedScreen)
                    is ChartsScreenState.Loading -> binding.chartView.showLoading()
                    is ChartsScreenState.Success -> binding.chartView.showChart(state.chart)
                }
            }
        }
    }
}
