package br.com.fomezero.joaofood.util

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitUtil {

    private val okHttpClient: OkHttpClient = okHttpClientCreator()

    private fun okHttpClientCreator(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)

        return builder.build()
    }

    private fun getRetrofit(baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    fun <T> getApiService(baseUrl: String, clazz: Class<T>): T = getRetrofit(baseUrl).create(clazz)
}