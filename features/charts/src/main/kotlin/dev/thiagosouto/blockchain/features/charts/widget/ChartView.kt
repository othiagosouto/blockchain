package dev.thiagosouto.blockchain.features.charts.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dev.thiagosouto.blockchain.domain.Axis
import dev.thiagosouto.blockchain.domain.Chart
import dev.thiagosouto.blockchain.features.charts.R
import dev.thiagosouto.blockchain.features.charts.databinding.FeaturesChartsViewChartBinding
import dev.thiagosouto.blockchain.features.charts.databinding.FeaturesChartsViewChartErrorBinding
import dev.thiagosouto.blockchain.features.charts.databinding.FeaturesChartsViewChartLoadingBinding
import dev.thiagosouto.blockchain.features.commons.ext.getThemeColorFrom

class ChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val contentBinding: FeaturesChartsViewChartBinding
    private val loadingBinding: FeaturesChartsViewChartLoadingBinding
    private val errorBinding: FeaturesChartsViewChartErrorBinding

    init {
        val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        contentBinding = FeaturesChartsViewChartBinding.inflate(layoutInflater, this, true)
        loadingBinding = FeaturesChartsViewChartLoadingBinding.bind(contentBinding.root)
        errorBinding = FeaturesChartsViewChartErrorBinding.bind(contentBinding.root)
    }

    fun showLoading() {
        errorBinding.errorContent.visibility = View.GONE
        contentBinding.loadedContent.visibility = View.GONE
        loadingBinding.shimmerViewContainer.visibility = View.VISIBLE
    }

    fun showError(title: String, description: String, retryAction: () -> Unit) {
        errorBinding.errorTitle.text = title
        errorBinding.errorDescription.text = description
        errorBinding.buttonRetry.setOnClickListener {
            retryAction()
        }
        contentBinding.loadedContent.visibility = View.GONE
        loadingBinding.shimmerViewContainer.visibility = View.GONE
        errorBinding.errorContent.visibility = View.VISIBLE
    }

    fun showChart(chart: Chart) {
        contentBinding.title.text = chart.name
        contentBinding.description.text = chart.description
        contentBinding.caption.text = chart.name
        contentBinding.chart.run {
            setBackgroundColor(Color.TRANSPARENT)
            description.isEnabled = false
            setTouchEnabled(true)
            legend.isEnabled = false
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            xAxis.setup(context)
            axisRight.isEnabled = false
            axisLeft.setMaxAndMin(context, chart.axis)
            animateX(animateDuration)
            data = LineData(listOf(chart.toEntries().createDataSet(context, chart.name)))
        }
        contentBinding.loadedContent.visibility = View.VISIBLE
        loadingBinding.shimmerViewContainer.visibility = View.GONE
    }

    private fun Chart.toEntries(): List<Entry> = mutableListOf<Entry>().apply {
        axis.forEach {
            add(Entry(it.timestamp.toFloat(), it.value))
        }
    }

    private fun YAxis.setMaxAndMin(context: Context, axis: List<Axis>) {
        axisMaximum = axis.maxOf { it.value }
        axisMinimum = axis.minOf { it.value }
        axisLineColor = context.getThemeColorFrom(android.R.attr.textColorPrimary)
        textColor = context.getThemeColorFrom(android.R.attr.textColorPrimary)
        textSize = yAxisTextSize
    }

    private fun XAxis.setup(context: Context) {
        axisLineColor = context.getThemeColorFrom(android.R.attr.textColorPrimary)
        this.setDrawGridLines(false)
        this.isEnabled = false
        this.setDrawAxisLine(false)
    }

    private fun List<Entry>.createDataSet(context: Context, title: String): LineDataSet =
        LineDataSet(this, title).apply {
            val secondaryColor = context.getThemeColorFrom(R.attr.colorSecondary)
            color = secondaryColor
            setCircleColor(secondaryColor)
            setDrawValues(false)
            circleRadius = dataSetEntryCircleRadius
            setDrawCircleHole(true)
        }

    companion object {
        private const val yAxisTextSize = 14f
        private const val dataSetEntryCircleRadius = 3f
        private const val animateDuration = 1500
    }
}
