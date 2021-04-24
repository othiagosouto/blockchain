package dev.thiagosouto.blockchain.features.charts.widget

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [23])
class ChartViewTest {

    @Test
    fun shouldPresentLoading_when_showLoadingIsCalled() {
        launchChartView {
            applyLoading()
        } check {
            loadingVisible()
            loadingContentDescription()
            contentNotVisible()
            errorNotVisible()
        }
    }

    @Test
    fun shouldPresentContent() {
        launchChartView {
            applyContent()
        } check {
            loadingNotVisible()
            errorNotVisible()
            contentDisplayed()
            contentDisplayedWithExpectedValues()
        }
    }

    @Test
    fun showPresentErrorView_when_showErrorIsCalled() {
        launchChartView {
            applyError()
        } check {
            loadingNotVisible()
            contentNotVisible()
            errorVisible()
            checkErrorContent()
        }
    }

    @Test
    fun shouldCallRetryAction_when_retryButtonIsClicked() {
        launchChartView {
            applyError()
        } perform {
            clickRetry()
        } check {
            retryActionFired()
        }
    }
}
