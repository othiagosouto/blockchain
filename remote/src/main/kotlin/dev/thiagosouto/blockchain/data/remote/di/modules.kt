package dev.thiagosouto.blockchain.data.remote.di

import dev.thiagosouto.blockchain.data.remote.RetrofitFactory
import dev.thiagosouto.blockchain.data.remote.features.charts.ChartRemoteSource
import dev.thiagosouto.blockchain.data.remote.features.charts.ChartsApi
import dev.thiagosouto.blockchain.domain.RemoteSource
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

@ExperimentalSerializationApi
val remoteModule: Module = module {
    single<RemoteSource> {
        ChartRemoteSource(RetrofitFactory().create(get(named("SERVER_URL"))).create(ChartsApi::class.java))
    }
}
