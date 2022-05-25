package com.pourkazemi.mahdi.kalastore.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.pourkazemi.mahdi.kalastore.App.Companion.BASE_URL
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.KalaCategory
import com.pourkazemi.mahdi.kalastore.data.model.KalaCategoryDeserializer
import com.pourkazemi.mahdi.kalastore.data.model.KalaDeserializer
import com.pourkazemi.mahdi.kalastore.data.remote.KalaApi
import com.pourkazemi.mahdi.kalastore.data.remote.RemoteDataSource
import com.pourkazemi.mahdi.kalastore.data.remote.RemoteDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRemoteDataSourceIm(
        kalaApi: KalaApi
    ): RemoteDataSource =
        RemoteDataSourceImp(kalaApi)

    @Provides
    @Singleton
    fun provideKalaApiService(retrofit: Retrofit): KalaApi {
        return retrofit.create(KalaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(15, TimeUnit.SECONDS) // connect timeout
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideGsonFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun providesGson(): Gson = GsonBuilder().registerTypeAdapter(
        object : TypeToken<MutableList<Kala>>() {}.type,
        KalaDeserializer(),
    ).registerTypeAdapter(
        object : TypeToken<MutableList<KalaCategory>>() {}.type,
        KalaCategoryDeserializer(),
    ).create()
}