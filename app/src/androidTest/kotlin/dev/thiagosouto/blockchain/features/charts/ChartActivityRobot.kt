package dev.thiagosouto.blockchain.features.charts

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withTagValue
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.util.HumanReadables
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeoutException

fun launchActivity(tag: String, func: ChartsActivityConfiguration.() -> Unit = {}) =
    ChartsActivityConfiguration(tag).apply(func)

class ChartsActivityConfiguration(private val tag: String) {
    private val server = MockWebServer()

    init {
        server.start()
        val url = server.url("").toString()
        loadKoinModules(
            module(override = true) {
                single(named("SERVER_URL")) { url }
            }
        )
        ActivityScenario.launch(ChartsActivity::class.java)
    }

    infix fun check(func: ChartsActivityResult.() -> Unit): ChartsActivityResult {
        return ChartsActivityResult(server).apply(func)
    }

    infix fun perform(func: ChartsActivityActions.() -> Unit) =
        ChartsActivityActions(tag, server).apply(func)

    fun applySuccessCase() {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/charts/market-price?timespan=5weeks" -> MockResponse().setBody("stubs/market_price.json".readAsText())
                    "/charts/n-transactions?timespan=5weeks" -> MockResponse().setBody("stubs/n_transactions.json".readAsText())
                    "/charts/total-bitcoins?timespan=5weeks" -> MockResponse().setBody("stubs/total_bitcoins.json".readAsText())
                    else -> MockResponse().setResponseCode(400)
                }
            }
        }
    }

    fun retryScenario() {
        server.enqueue(MockResponse().setResponseCode(400))
        server.enqueue(MockResponse().setBody("stubs/market_price.json".readAsText()))
    }

}

class ChartsActivityActions(private val tag: String, private val server: MockWebServer) {
    infix fun check(func: ChartsActivityResult.() -> Unit) =
        ChartsActivityResult(server).apply(func)

    fun waitUntilLoadingIsGone() {
        onView(withTagValue(`is`(tag))).perform(waitUntilLoadingNotVisible(5000L))
    }

    fun clickOnRetryButton() {
        onView(withId(R.id.button_retry)).perform(click())
    }

    fun swipeLeft() {
        onView(withId(R.id.pager)).perform(ViewActions.swipeLeft())
    }
}

class ChartsActivityResult(private val server: MockWebServer) {

    fun checkBitcoinCirculationMessage() {
        onView(withId(R.id.pager)).check(ViewAssertions.matches(hasDescendant(withText("The total number of bitcoins that have already been mined; in other words, the current supply of bitcoins on the network."))))
        onView(withId(R.id.pager)).check(ViewAssertions.matches(hasDescendant(withText("Bitcoins in circulation"))))
    }

    fun checkNTransactionsMessage() {
        onView(withId(R.id.pager)).check(ViewAssertions.matches(hasDescendant(withText("The number of daily confirmed Bitcoin transactions."))))
        onView(withId(R.id.pager)).check(ViewAssertions.matches(hasDescendant(withText("Confirmed Transactions Per Day"))))
    }

    fun checkMarketPRiceMessage() {
        onView(withId(R.id.pager)).check(ViewAssertions.matches(hasDescendant(withText("Average USD market price across major bitcoin exchanges."))))
        onView(withId(R.id.pager)).check(ViewAssertions.matches(hasDescendant(withText("Market Price (USD)"))))
    }

    fun closeSever() {
        server.shutdown()
    }
}

private fun String.readAsText() =
    InstrumentationRegistry.getInstrumentation().context.assets.open(this).bufferedReader()
        .use { it.readText() }


/**
 * A [ViewAction] that waits up to [timeout] milliseconds for a [View]'s visibility value to change to [View.VISIBLE].
 * source: https://adilatwork.blogspot.com/2020/08/espresso-tests-wait-until-view-is.html
 */
fun waitUntilLoadingNotVisible(timeout: Long): ViewAction {
    return WaitUntilNotVisibleAction(timeout, R.id.shimmer_view_container)
}

class WaitUntilNotVisibleAction(private val timeout: Long, private val id: Int) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return Matchers.any(View::class.java)
    }

    override fun getDescription(): String {
        return "wait up to $timeout milliseconds for the view to become not visible"
    }

    override fun perform(uiController: UiController, view: View) {

        val endTime = System.currentTimeMillis() + timeout

        do {
            val content = view.findViewById<View>(id)
            if (content.visibility == View.GONE) return
            uiController.loopMainThreadForAtLeast(50)
        } while (System.currentTimeMillis() < endTime)

        throw PerformException.Builder()
            .withActionDescription(description)
            .withCause(TimeoutException("Waited $timeout milliseconds"))
            .withViewDescription(HumanReadables.describe(view))
            .build()
    }
}