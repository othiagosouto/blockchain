package dev.thiagosouto.blockchain.features.charts.di

import dev.thiagosouto.blockchain.features.charts.ChartsProperties
import dev.thiagosouto.blockchain.features.charts.ChartsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chartsModules = module {
    viewModel { ChartsViewModel(get()) }
    factory { ChartsProperties() }
}
