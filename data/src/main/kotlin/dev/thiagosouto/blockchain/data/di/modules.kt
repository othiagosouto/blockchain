package dev.thiagosouto.blockchain.data.di

import dev.thiagosouto.blockchain.data.ChartRepositoryImpl
import dev.thiagosouto.blockchain.domain.ChartRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule: Module = module {
    single<ChartRepository> {
        ChartRepositoryImpl(get())
    }
}
