package dev.thiagosouto.blockchain.features.charts.widget

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import dev.thiagosouto.blockchain.domain.Axis
import dev.thiagosouto.blockchain.domain.Chart
import dev.thiagosouto.blockchain.features.charts.R
import dev.thiagosouto.blockchain.features.charts.test.TestActivity
import org.hamcrest.Matchers

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
    fun applyLoading() {
        scenario.onActivity {
            it.findViewById<ChartView>(viewId).showLoading()
        }
    }
}

class ChartViewResult(private val chart: Chart) {
    fun shimmerVisible() {
        println(R.id.shimmer_view_container)
        onView(withId(R.id.shimmer_view_container)).check(matches(isDisplayed()))
    }

    fun shimmerContentDescription() {
        onView(withId(R.id.shimmer_view_container)).check(
            matches(
                ViewMatchers.withContentDescription(
                    "Loading chart"
                )
            )
        )
    }

    fun shimmerIsNotVisible() {
        onView(withId(R.id.shimmer_view_container)).check(matches(Matchers.not(isDisplayed())))
    }

    fun loadedContentInvisible() {
        onView(withId(R.id.chart)).check(matches(Matchers.not(isDisplayed())))
        onView(withId(R.id.description)).check(matches(Matchers.not(isDisplayed())))
        onView(withId(R.id.title)).check(matches(Matchers.not(isDisplayed())))
        onView(withId(R.id.caption)).check(matches(Matchers.not(isDisplayed())))
    }

    fun contentDisplayed() {
        onView(withId(R.id.chart)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.title)).check(matches(isDisplayed()))
        onView(withId(R.id.caption)).check(matches(isDisplayed()))
    }

    fun contentDisplayedWithExpectedValues() {
        onView(withId(R.id.description)).check(matches(withText(chart.description)))
        onView(withId(R.id.title)).check(matches(withText(chart.name)))
        onView(withId(R.id.caption)).check(matches(withText(chart.name)))
    }
}