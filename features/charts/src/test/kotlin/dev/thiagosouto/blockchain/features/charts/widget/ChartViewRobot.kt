package dev.thiagosouto.blockchain.features.charts.widget

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import dev.thiagosouto.blockchain.domain.Axis
import dev.thiagosouto.blockchain.domain.Chart
import dev.thiagosouto.blockchain.features.charts.R
import dev.thiagosouto.blockchain.features.charts.test.TestActivity

fun launchChartView(func: ChartViewRobot.() -> Unit = {}) =
    ChartViewRobot().apply(func)

class ChartViewRobot {
    private val viewId: Int = 1332323
    private val chart: Chart =
        Chart(
            listOf(Axis(System.currentTimeMillis(), 100f)),
            "some description",
            "title",
            "USD",
            "market-price"
        )
    private val scenario: ActivityScenario<TestActivity> = launch(TestActivity::class.java)

    init {
        scenario.onActivity {
            val view = ChartView(it).apply { id = viewId }
            it.setContentView(view)
        }
    }

    infix fun check(func: ChartViewResult.() -> Unit) =
        ChartViewResult(chart).apply(func)

    fun applyContent() {
        scenario.onActivity {
            it.findViewById<ChartView>(viewId).showChart(chart)
        }
    }
}

class ChartViewResult(private val chart: Chart) {
    fun contentDisplayed() {
        onView(withId(R.id.chart)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.title)).check(matches(isDisplayed()))
        onView(withId(R.id.caption)).check(matches(isDisplayed()))
    }

    fun contentDisplayedWithExpectedValues() {
        onView(withId(R.id.chart)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(withText(chart.description)))
        onView(withId(R.id.title)).check(matches(withText(chart.name)))
        onView(withId(R.id.caption)).check(matches(withText(chart.name)))
    }
}