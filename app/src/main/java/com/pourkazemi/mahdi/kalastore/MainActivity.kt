package com.pourkazemi.mahdi.kalastore

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pourkazemi.mahdi.kalastore.databinding.ActivityMainBinding
import com.pourkazemi.mahdi.kalastore.ui.category.CategoryViewModel
import com.pourkazemi.mahdi.kalastore.ui.home.ShearedViewModel
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
        binding.bottomNavigation.background = null
        binding.bottomNavigation.menu.getItem(2).isEnabled = false


        binding.toolbar.ShapeableImageView.setOnClickListener {
            mNavHost.navController.navigate(R.id.to_searchFragment)
        }
        binding.fab.setOnClickListener {
           mNavHost.navController.navigate(R.id.to_cartFragment)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityViewModel.networkState.collect {
                    when (it) {
                        is NetworkStatus.Available -> {
                            activityViewModel.getListProduct()
                            categoryViewModel.getListKalaCategory()
                            mNavHost.navController.popBackStack(R.id.noInternetFragment, true)
                        }
                        is NetworkStatus.Unavailable -> {
                            mNavHost.navController.navigate(R.id.to_noInternetFragment)
                        }
                    }
                }
            }
        }
        mNavHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchFragment -> {
                    binding.bottomNavigationLayout.visibility=View.GONE
                    binding.toolbar.ShapeableImageView.visibility = View.GONE
                }
                R.id.detailFragment ->binding.bottomNavigationLayout.visibility=View.GONE
                //R.id.cartFragment -> binding.bottomNavigationLayout.visibility=View.GONE
                else->{
                    binding.bottomNavigationLayout.visibility=View.VISIBLE
                    binding.toolbar.ShapeableImageView.visibility = View.VISIBLE
                }
            }
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


