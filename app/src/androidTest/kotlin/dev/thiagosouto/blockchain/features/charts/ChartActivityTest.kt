package dev.thiagosouto.blockchain.features.charts

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class ChartActivityTest {
    private lateinit var server: MockWebServer

    @Before
    fun setup() = runBlocking {
        server = MockWebServer()
        server.start()
        val url = server.url("").toString()

        loadKoinModules(
            module(override = true) {
                single(named("SERVER_URL")) { url }
            }
        )
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun shouldDisplayMarketPrice_when_successfulResponse() {
        server.dispatcher = successfulDispatcher()
        ActivityScenario.launch(ChartsActivity::class.java)

        onView(withId(R.id.description)).check(matches(withText("Average USD market price across major bitcoin exchanges.")))
        onView(withId(R.id.caption)).check(matches(withText("Market Price (USD)")))
    }

    @Test
    fun shouldDisplayTransactionsByDay_when_successfulResponse() {
        server.dispatcher = successfulDispatcher()
        ActivityScenario.launch(ChartsActivity::class.java)

        onView(withId(R.id.pager)).perform(swipeLeft())
        onView(withId(R.id.description)).check(matches(withText("The number of daily confirmed Bitcoin transactions.")))
        onView(withId(R.id.caption)).check(matches(withText("Confirmed Transactions Per Day")))
    }

    @Test
    fun shouldDisplayBitcoinInCirculation_when_successfulResponse() {
        server.dispatcher = successfulDispatcher()
        ActivityScenario.launch(ChartsActivity::class.java)

        onView(withText("Bitcoins in circulation")).perform(click())

        onView(withId(R.id.description)).check(matches(withText("The total number of bitcoins that have already been mined; in other words, the current supply of bitcoins on the network.")))
        onView(withId(R.id.caption)).check(matches(withText("Bitcoins in circulation")))
    }

    @Test
    fun shouldDisplayContent_when_clickedOnRetry() {
        server.enqueue(MockResponse().setResponseCode(400))
        server.enqueue(MockResponse().setBody("stubs/market_price.json".readAsText()))
        ActivityScenario.launch(ChartsActivity::class.java)

        onView(withId(R.id.button_retry)).perform(click())

        onView(withId(R.id.description)).check(matches(withText("Average USD market price across major bitcoin exchanges.")))
        onView(withId(R.id.caption)).check(matches(withText("Market Price (USD)")))
    }

    private fun successfulDispatcher() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/charts/market-price?timespan=5weeks" -> MockResponse().setBody("stubs/market_price.json".readAsText())
                "/charts/n-transactions?timespan=5weeks" -> MockResponse().setBody("stubs/n_transactions.json".readAsText())
                "/charts/total-bitcoins?timespan=5weeks" -> MockResponse().setBody("stubs/total_bitcoins.json".readAsText())
                else -> MockResponse().setResponseCode(400)
            }
        }
    }

    private fun String.readAsText() =
        getInstrumentation().context.assets.open(this).bufferedReader().use { it.readText() }
}