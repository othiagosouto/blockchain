package dev.thiagosouto.blockchain.features.charts.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
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

class ChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: FeaturesChartsViewChartBinding

    init {
        val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = FeaturesChartsViewChartBinding.inflate(layoutInflater, this, true)
    }

    fun showChart(chart: Chart) {
        binding.title.text = chart.name
        binding.description.text = chart.description
        binding.caption.text = chart.name
        binding.chart.setBackgroundColor(Color.TRANSPARENT)
        binding.chart.description.isEnabled = false
        binding.chart.setTouchEnabled(true)
        binding.chart.legend.isEnabled = false
        binding.chart.isDragEnabled = true
        binding.chart.setScaleEnabled(true)
        binding.chart.setPinchZoom(true)

        binding.chart.xAxis.setup(context)

        binding.chart.axisRight.isEnabled = false

        binding.chart.axisLeft.setMaxAndMin(context, chart.axis)
        binding.chart.animateX(animateDuration)
        binding.chart.data = LineData(listOf(chart.toEntries().createDataSet(context, chart.name)))
    }

    private fun Chart.toEntries(): List<Entry> = mutableListOf<Entry>().apply {
        axis.forEach {
            add(Entry(it.timestamp.toFloat(), it.value))
        }
    }

    private fun YAxis.setMaxAndMin(context: Context, axis: List<Axis>) {
        axisMaximum = axis.maxOf { it.value }
        axisMinimum = axis.minOf { it.value }
        axisLineColor = getThemeColor(context, android.R.attr.textColorPrimary)
        textColor = getThemeColor(context, android.R.attr.textColorPrimary)
        textSize = yAxisTextSize
    }

    private fun XAxis.setup(context: Context) {
        axisLineColor = getThemeColor(context, android.R.attr.textColorPrimary)
        this.setDrawGridLines(false)
        this.isEnabled = false
        this.setDrawAxisLine(false)
    }

    private fun List<Entry>.createDataSet(context: Context, title: String): LineDataSet =
        LineDataSet(this, title).apply {
            val secondaryColor = getThemeColor(context, R.attr.colorSecondary)
            color = secondaryColor
            setCircleColor(secondaryColor)
            setDrawValues(false)
            circleRadius = dataSetEntryCircleRadius
            setDrawCircleHole(true)
        }

    @ColorInt
    private fun getThemeColor(
        context: Context,
        @AttrRes attrs: Int
    ): Int {
        val a = context.obtainStyledAttributes(null, intArrayOf(attrs))
        try {
            return a.getColor(0, Color.MAGENTA)
        } finally {
            a.recycle()
        }
    }

    companion object {
        private const val yAxisTextSize = 14f
        private const val dataSetEntryCircleRadius = 3f
        private const val animateDuration = 1500
    }
}
