package com.pourkazemi.mahdi.kalastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.pourkazemi.mahdi.kalastore.databinding.ActivityMainBinding
import com.pourkazemi.mahdi.kalastore.ui.viewmodels.ShearedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val activityViewModel: ShearedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            activityViewModel.splashScreen.value
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mNavHost =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(mNavHost.navController)


    }
}


