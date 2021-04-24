package dev.thiagosouto.blockchain.di

import dev.thiagosouto.blockchain.BuildConfig
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModules = module {
    single(named("SERVER_URL")) { BuildConfig.SERVER_URL }
}
