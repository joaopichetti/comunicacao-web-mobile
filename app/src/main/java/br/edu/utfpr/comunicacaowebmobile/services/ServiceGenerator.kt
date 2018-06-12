package br.edu.utfpr.comunicacaowebmobile.services

import br.edu.utfpr.comunicacaowebmobile.util.converters.NullOnEmptyConverterFactory
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceGenerator {
    companion object {
        private const val API_HOST = "http://192.168.2.103:8080/"

        private val httpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

        private val builder = Retrofit.Builder()
                .baseUrl(API_HOST).client(httpClient)
                .addConverterFactory(NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()))

        private val retrofit = builder.build()

        fun <S> createService(serviceClass: Class<S>): S {
            return retrofit.create(serviceClass)
        }
    }
}