package com.xtanion.splashwalls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.global_appbar)
        bottomNavigationView.setupWithNavController(navController.navController)
    }
}