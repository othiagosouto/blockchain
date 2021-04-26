package dev.thiagosouto.blockchain

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import dev.thiagosouto.blockchain.features.charts.ChartsActivity

internal class LaunchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, ChartsActivity::class.java)
        startActivity(intent)
        finish()
    }
}
