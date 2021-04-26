package dev.thiagosouto.blockchain.features.charts

import androidx.annotation.StringRes
import dev.thiagosouto.blockchain.domain.Chart

internal sealed class ChartsScreenState {
    object Idle : ChartsScreenState()
    object Loading : ChartsScreenState()
    data class Success(val chart: Chart) : ChartsScreenState()
    data class Failed(
        @StringRes val titleResId: Int,
        @StringRes val descriptionResId: Int
    ) : ChartsScreenState()
}
