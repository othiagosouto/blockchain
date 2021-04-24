package dev.thiagosouto.blockchain.data.remote.features.charts

import dev.thiagosouto.blockchain.domain.exception.InternetNotAvailableException
import dev.thiagosouto.blockchain.domain.exception.UnexpectedErrorException
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

@Suppress("ThrowsCount")
internal suspend fun <T> remoteCall(func: suspend () -> T): T {
    try {
        return func()
    } catch (e: UnknownHostException) {
        throw InternetNotAvailableException(e)
    } catch (e: HttpException) {
        throw UnexpectedErrorException(e)
    } catch (e: IOException) {
        throw UnexpectedErrorException(e)
    }
}
