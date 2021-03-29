package com.dyamschikov.newsapiapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.dyamschikov.newsapiapp.databinding.ActivityMainBinding
import com.dyamschikov.newsapiapp.utils.extensions.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var currentNavController: LiveData<NavController>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {

        val navGraphIds = listOf(R.navigation.nav_headlines, R.navigation.nav_everything, R.navigation.nav_settings)
        /** Setup the bottom navigation view with a list of navigation graphs */
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )
        /** Whenever the selected controller changes, setup the action bar. */
        controller.observe(this, { navController ->
            setupActionBarWithNavController(navController)
        })

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController.value?.navigateUp() ?: false
    }
}