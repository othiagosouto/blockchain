package dev.thiagosouto.blockchain.features.charts.widget

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import dev.thiagosouto.blockchain.domain.Axis
import dev.thiagosouto.blockchain.domain.Chart
import dev.thiagosouto.blockchain.features.charts.R
import dev.thiagosouto.blockchain.features.charts.test.TestActivity
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.Matchers.not

fun launchChartView(func: ChartViewRobot.() -> Unit = {}) =
    ChartViewRobot().apply(func)

class ChartViewRobot {
    private val retryAction: () -> Unit = mockk(relaxed = true)
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
        ChartViewResult(chart, retryAction).apply(func)

    infix fun perform(func: ChartViewActions.() -> Unit) =
        ChartViewActions(chart, retryAction).apply(func)

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

    fun applyError() {
        scenario.onActivity {
            it.findViewById<ChartView>(viewId)
                .showError("error title", "error description", retryAction)
        }
    }
}

class ChartViewActions(private val chart: Chart, private val retryAction: () -> Unit) {
    infix fun check(func: ChartViewResult.() -> Unit) =
        ChartViewResult(chart, retryAction).apply(func)

    fun clickRetry() {
        onView(withId(R.id.button_retry)).perform(ViewActions.click())
    }
}

class ChartViewResult(private val chart: Chart, val retryAction: () -> Unit = {}) {
    fun loadingVisible() {
        println(R.id.shimmer_view_container)
        onView(withId(R.id.shimmer_view_container)).check(matches(isDisplayed()))
    }

    fun loadingContentDescription() {
        onView(withId(R.id.shimmer_view_container)).check(
            matches(
                ViewMatchers.withContentDescription(
                    "Loading chart"
                )
            )
        )
    }

    fun loadingNotVisible() {
        onView(withId(R.id.shimmer_view_container)).check(matches(not(isDisplayed())))
    }

    fun contentNotVisible() {
        onView(withId(R.id.chart)).check(matches(not(isDisplayed())))
        onView(withId(R.id.description)).check(matches(not(isDisplayed())))
        onView(withId(R.id.caption)).check(matches(not(isDisplayed())))
    }

    fun contentDisplayed() {
        onView(withId(R.id.chart)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.caption)).check(matches(isDisplayed()))
    }

    fun contentDisplayedWithExpectedValues() {
        onView(withId(R.id.description)).check(matches(withText(chart.description)))
        onView(withId(R.id.caption)).check(matches(withText(chart.name)))
    }

    fun errorVisible() {
        onView(withId(R.id.error_image)).check(matches(isDisplayed()))
        onView(withId(R.id.error_description)).check(matches(isDisplayed()))
        onView(withId(R.id.error_title)).check(matches(isDisplayed()))
    }

    fun errorNotVisible() {
        onView(withId(R.id.error_image)).check(matches(not(isDisplayed())))
        onView(withId(R.id.error_description)).check(matches(not(isDisplayed())))
        onView(withId(R.id.error_title)).check(matches(not(isDisplayed())))
    }

    fun checkErrorContent() {
        onView(withId(R.id.error_title)).check(matches(withText("error title")))
        onView(withId(R.id.error_description)).check(matches(withText("error description")))
    }

    fun retryActionFired() {
        verify { retryAction() }
    }

}