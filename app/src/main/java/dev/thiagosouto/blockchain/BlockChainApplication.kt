package dev.thiagosouto.blockchain

import android.app.Application
import dev.thiagosouto.blockchain.data.di.dataModule
import dev.thiagosouto.blockchain.data.remote.di.remoteModule
import dev.thiagosouto.blockchain.di.appModules
import dev.thiagosouto.blockchain.features.charts.di.chartsModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BlockChainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BlockChainApplication)
            modules(appModules + chartsModules + dataModule + remoteModule)
        }
    }
}
