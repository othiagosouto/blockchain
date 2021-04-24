package dev.thiagosouto.blockchain.features.charts

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChartsActivityTest {

    @Test
    fun shouldInitScreenCorrectly() {
        ActivityScenario.launch(ChartsActivity::class.java)
    }

}
