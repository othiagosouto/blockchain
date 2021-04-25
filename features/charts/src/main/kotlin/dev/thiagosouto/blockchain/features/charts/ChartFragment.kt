package dev.thiagosouto.blockchain.features.charts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dev.thiagosouto.blockchain.features.charts.ChartsInteractions.ClickedOnRetry
import dev.thiagosouto.blockchain.features.charts.ChartsInteractions.OpenedScreen
import dev.thiagosouto.blockchain.features.charts.ChartsScreenState.Failed
import dev.thiagosouto.blockchain.features.charts.ChartsScreenState.Idle
import dev.thiagosouto.blockchain.features.charts.ChartsScreenState.Loading
import dev.thiagosouto.blockchain.features.charts.ChartsScreenState.Success
import dev.thiagosouto.blockchain.features.charts.databinding.FeaturesChartsFragmentChartBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChartFragment : Fragment() {
    private val chartsViewModel: ChartsViewModel by viewModel()
    private lateinit var binding: FeaturesChartsFragmentChartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FeaturesChartsFragmentChartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chartId = arguments?.getString(CHART_ID) ?: "market-price"
        lifecycleScope.launchWhenCreated {
            chartsViewModel.bind().collect { state ->
                when (state) {
                    is Idle -> chartsViewModel.interact(OpenedScreen(chartId))
                    is Loading -> binding.chartView.showLoading()
                    is Success -> binding.chartView.showChart(state.chart)
                    is Failed -> binding.chartView.showError(
                        getString(state.titleResId),
                        getString(state.descriptionResId)
                    ) { chartsViewModel.interact(ClickedOnRetry("market-price")) }
                }
            }
        }
    }

    companion object {
        private const val CHART_ID = "CHART_ID"
        fun newInstance(id: String): Fragment {
            val chartFragment = ChartFragment()
            chartFragment.arguments = Bundle().apply { putString(CHART_ID, id) }

            return chartFragment
        }
    }
}
