package com.example.asus.week2.Api



import com.example.asus.week2.BuildConfig
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class Api {

    companion object {
        private const val BASE_URL = "https://api.nytimes.com/svc/search/v2/"
        private const val LANGUAGE = "vi"
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        private fun builder(): Retrofit {
            return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client())
                .build()
        }

        private fun client(): OkHttpClient {
            return OkHttpClient.Builder().addNetworkInterceptor { chain ->
                var request = chain.request()
                val url = request.url().newBuilder().addQueryParameter("api-key", BuildConfig.API_KEY)
//                    .addQueryParameter("language", LANGUAGE)
                    .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }.apply {
                this.addInterceptor(interceptor)
            }.build()
        }

        fun createService(): NewYorkTimeSearchApi {
            return builder().create(NewYorkTimeSearchApi::class.java)
        }
    }
}