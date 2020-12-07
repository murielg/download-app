package com.gonzoapps.downloadapp

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.gonzoapps.downloadapp.databinding.ActivityMainBinding
import com.gonzoapps.downloadapp.receiver.DownloadReceiver
import com.gonzoapps.downloadapp.ui.loadingbutton.ButtonState
import com.gonzoapps.downloadapp.ui.main.DownloadListFragment


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    lateinit var binding: ActivityMainBinding

    private lateinit var downloadReceiver : DownloadReceiver

    private lateinit var navHostFragment : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupNavigation()
        registerDownloadReceiver()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
    }

    private fun registerDownloadReceiver() {

        downloadReceiver = object  : DownloadReceiver() {
            override fun broadcastDownloadState(state: ButtonState) {
                if (navController.currentDestination?.id == R.id.mainFragment) {
                    val fragmentInstance = navHostFragment.childFragmentManager.primaryNavigationFragment as DownloadListFragment
                    fragmentInstance.updateDownloadButtonWithStatus(state)
                }

            }
        }
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        this.registerReceiver(downloadReceiver, filter)

    }

    private fun setupNavigation() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.findNavController()

        setSupportActionBar(binding.toolbar)

        setupActionBarWithNavController(navController)
    }
}
