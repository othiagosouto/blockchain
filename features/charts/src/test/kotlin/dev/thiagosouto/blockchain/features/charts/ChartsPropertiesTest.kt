package dev.thiagosouto.blockchain.features.charts

import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class ChartsPropertiesTest {

    @Test
    fun `should contains expected titles`() {
        val expectedTitles = listOf(
            "Market price",
            "Confirmed Transactions",
            "Bitcoins in circulation"
        )
        assertThat(ChartsProperties().titles).isEqualTo(expectedTitles)
    }

    @Test
    fun `should contains expected ids`() {
        val expectedIds = listOf(
            "market-price",
            "n-transactions",
            "total-bitcoins"
        )
        assertThat(ChartsProperties().ids).isEqualTo(expectedIds)

    }
}