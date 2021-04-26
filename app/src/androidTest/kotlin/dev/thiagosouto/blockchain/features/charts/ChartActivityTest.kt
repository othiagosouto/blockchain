package dev.thiagosouto.blockchain.features.charts

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ChartActivityTest {

    @Test
    fun shouldDisplayMarketPrice_when_successfulResponse() {
        launchActivity("market-price") {
            applySuccessCase()
        } perform {
            waitUntilLoadingIsGone()
        } check {
            checkMarketPRiceMessage()
            closeSever()
        }
    }

    @Test
    fun shouldDisplayTransactionsByDay_when_successfulResponse() {
        launchActivity("n-transactions") {
            applySuccessCase()
        } perform {
            swipeLeft()
            waitUntilLoadingIsGone()
        } check {
            checkNTransactionsMessage()
            closeSever()
        }
    }

    @Test
    fun shouldDisplayBitcoinInCirculation_when_successfulResponse() {
        launchActivity("total-bitcoins") {
            applySuccessCase()
        } perform {
            swipeLeft()
            swipeLeft()
            waitUntilLoadingIsGone()
        } check {
            checkBitcoinCirculationMessage()
            closeSever()
        }
    }

    @Test
    fun shouldDisplayContent_when_clickedOnRetry() {
        launchActivity("market-price") {
            retryScenario()
        } perform {
            waitUntilLoadingIsGone()
            clickOnRetryButton()
            waitUntilLoadingIsGone()
        } check {
            checkMarketPRiceMessage()
            closeSever()
        }
    }
}
