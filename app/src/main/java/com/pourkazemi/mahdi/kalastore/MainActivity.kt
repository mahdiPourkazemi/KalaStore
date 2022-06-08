package com.pourkazemi.mahdi.kalastore

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.internal.ContextUtils.getActivity
import com.pourkazemi.mahdi.kalastore.databinding.ActivityMainBinding
import com.pourkazemi.mahdi.kalastore.ui.category.CategoryViewModel
import com.pourkazemi.mahdi.kalastore.ui.home.ShearedViewModel
import com.pourkazemi.mahdi.kalastore.ui.internet.NoInternetFragment
import com.pourkazemi.mahdi.kalastore.utils.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val activityViewModel: ShearedViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()

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


        binding.toolbar.ShapeableImageView.setOnClickListener {
            mNavHost.navController.navigate(R.id.searchFragment)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityViewModel.networkState.collect {
                    when (it) {
                        is NetworkStatus.Available -> {
                            Timber.tag("mahdiTest").d("Available")
                            activityViewModel.getListProduct()
                            categoryViewModel.getListKalaCategory()
                            mNavHost.navController.popBackStack(R.id.noInternetFragment, true)
                        }
                        is NetworkStatus.Unavailable -> {
                            Timber.tag("mahdiTest").d("Unavailable")
                            mNavHost.navController.navigate(R.id.noInternetFragment)
                        }
                    }
                }
            }
        }
        mNavHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.accountFragment -> visibilityBottomNavigation(true)
                R.id.homeFragment -> visibilityBottomNavigation(true)
                R.id.categoryFragment -> visibilityBottomNavigation(true)
                R.id.cartFragment -> visibilityBottomNavigation(true)
                R.id.searchFragment -> {
                    visibilityBottomNavigation(false)
                    binding.toolbar.ShapeableImageView.visibility = View.GONE
                }
                else -> visibilityBottomNavigation(false)
            }
        }
    }

    private fun visibilityBottomNavigation(visible: Boolean) {
        if (visible) {
            binding.toolbar.ShapeableImageView.visibility = View.VISIBLE
            binding.bottomNavigation.visibility = View.VISIBLE
        } else {
            binding.bottomNavigation.visibility = View.GONE
            binding.toolbar.ShapeableImageView.visibility = View.VISIBLE
        }
    }


    override fun onBackPressed() {
        val mNavHost =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
      if (mNavHost.navController.currentDestination?.id == R.id.noInternetFragment){
          moveTaskToBack(true)
      }else{
          super.onBackPressed()
      }
    }
}


