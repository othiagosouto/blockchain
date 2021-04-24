package dev.thiagosouto.blockchain.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

internal class RetrofitFactory {

    @kotlinx.serialization.ExperimentalSerializationApi
    fun create(serverUrl: String): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(serverUrl)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }
}
