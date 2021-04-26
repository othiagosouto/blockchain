package dev.thiagosouto.blockchain.features.commons.ext

import android.app.Application
import android.graphics.Color
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import dev.thiagosouto.blockchain.features.commons.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [23])
internal class ContextKtTest {

    @Test
    fun shouldGetExpectedColorAttribute() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        context.setTheme(R.style.Theme_BlockChainApp)
        val color = context.getThemeColorFrom(R.attr.colorPrimary)
        val gray90Color = -14277082
        assertThat(color).isEqualTo(gray90Color)
    }

    @Test
    fun shouldReturnDefaultColor() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        val color = context.getThemeColorFrom(R.attr.colorPrimary)
        assertThat(color).isEqualTo(Color.MAGENTA)
    }
}