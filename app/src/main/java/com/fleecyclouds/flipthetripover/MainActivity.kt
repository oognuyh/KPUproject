package com.fleecyclouds.flipthetripover

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host)
        bttmnavi.setupWithNavController(navController)

    }

    fun showBottomNavigationView() {
        bttmnavi.visibility = View.VISIBLE
    }

    fun hideBottomNavigationView() {
        bttmnavi.visibility = View.GONE
    }

}

