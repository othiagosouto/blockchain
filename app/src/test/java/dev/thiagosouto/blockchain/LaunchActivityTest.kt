package dev.thiagosouto.blockchain

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import dev.thiagosouto.blockchain.features.charts.ChartsActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [23])
class LaunchActivityTest {

    @Test
    fun shouldRedirectToChartsActivity() {
        Intents.init()
        val scenario = ActivityScenario.launch(LaunchActivity::class.java)

        Intents.intended(IntentMatchers.hasComponent(ChartsActivity::class.java.name))
        assertThat(scenario.state).isEqualTo(Lifecycle.State.DESTROYED)

        Intents.release()
    }

}