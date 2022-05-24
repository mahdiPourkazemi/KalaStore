package com.pourkazemi.mahdi.kalastore

import android.app.Application
import androidx.databinding.library.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant


@HiltAndroidApp
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        //if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        //}
    }
    companion object{
        const val BASE_URL="https://woocommerce.maktabsharif.ir/wp-json/wc/v3/"
        const val KEY="ck_c91e8d092377dc1b04dffcd3244791fa465c008e"
        const val SECRET="cs_0697d4f8163631488d96cf635e9e10e41ddc15d6"
    }
}