package dev.thiagosouto.blockchain.features.charts.widget

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [23])
class ChartViewTest {

    @Test
    fun shouldPresentContent() {
        launchChartView {
            applyContent()
        } check {
            contentDisplayed()
            contentDisplayedWithExpectedValues()
        }
    }
}