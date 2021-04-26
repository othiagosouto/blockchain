package dev.thiagosouto.blockchain.features.charts

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.facebook.testing.screenshot.Screenshot
import dev.thiagosouto.blockchain.domain.Axis
import dev.thiagosouto.blockchain.domain.Chart
import dev.thiagosouto.blockchain.features.charts.widget.ChartView
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ChartAcceptanceTest {

    @Test
    fun shouldInitChartProperly() {
        ActivityScenario.launch(ChartViewActivity::class.java)

        onView(ViewMatchers.withTagValue(`is`("chart-test"))).perform(chartScreenShot("shouldInitChartProperly"))
    }


    private fun chartScreenShot(testName: String): ViewAction {
        return ScreenshotAction(testName)
    }

    private class ScreenshotAction(private val testName: String) :
        ViewAction {

        override fun getConstraints(): Matcher<View> {
            return Matchers.any(View::class.java)
        }

        override fun getDescription(): String {
            return "Screenshot testing"
        }

        override fun perform(uiController: UiController, view: View) {
            Screenshot.snap(view).setName(testName).record()
        }
    }
}

class ChartViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val chart = Chart(
            axis = listOf(
                Axis(1000, 1.0f),
                Axis(2000, 2.0f),
                Axis(3000, 3.0f)
            ),
            description = "description",
            name = "Market price",
            unit = "USD",
            id = "market-price"
        )

        val chartView = ChartView(this)
        chartView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        chartView.showChart(chart)
        chartView.tag = "chart-test"
        setContentView(chartView)

    }
}
