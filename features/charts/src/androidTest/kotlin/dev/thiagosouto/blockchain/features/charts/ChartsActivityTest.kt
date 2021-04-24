package dev.thiagosouto.blockchain.features.charts

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.thiagosouto.blockchain.domain.Axis
import dev.thiagosouto.blockchain.domain.Chart
import dev.thiagosouto.blockchain.domain.ChartParameters
import dev.thiagosouto.blockchain.domain.ChartRepository
import dev.thiagosouto.blockchain.features.charts.di.chartsModules
import dev.thiagosouto.blockchain.features.charts.rules.KoinTestRule
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
@Ignore("This class is just to help me do manual testing when coding")
class ChartsActivityTest {

    @get:Rule
    val koinRule: KoinTestRule =
        KoinTestRule(listOf(chartsModules) + module { single<ChartRepository> { FakeRepository() } })

    @Test
    fun shouldInitScreenCorrectly() {
        ActivityScenario.launch(ChartsActivity::class.java)
        Thread.sleep(15_000)
    }
}

class FakeRepository : ChartRepository {
    override suspend fun getChart(params: ChartParameters): Chart =
        Chart(
            axis = listOf(
                Axis(1000, 1.0f),
                Axis(2000, 2.0f),
                Axis(3000, 3.0f)
            ),
            description = "description",
            name = "market-price",
            unit = "USD",
            id = "market-price"
        )
}