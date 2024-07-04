package com.management.myemployees.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.management.myemployees.R
import com.management.myemployees.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var i = 0
    private val mainActivityBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val myNavHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment }

    private val inflater by lazy { myNavHostFragment.navController.navInflater }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainActivityBinding.root)

        initBottomNavigationView()


    }

    private fun initBottomNavigationView(){

        mainActivityBinding.apply {
        bottomNavigation.itemIconTintList = null

            bottomNavigation.setOnItemSelectedListener {
                when(it.itemId){
                   R.id.myEmployee ->{
                        i = 0
                       if (bottomNavigation.selectedItemId != it.itemId){
                           val graph = inflater.inflate(R.navigation.navigation_employees)
                           myNavHostFragment.navController.graph = graph
                           return@setOnItemSelectedListener true
                       }


                   }

                    R.id.myProfile->{
                        i=0
                        if (bottomNavigation.selectedItemId != it.itemId){
                            val graph = inflater.inflate(R.navigation.navigation_profile)
                            myNavHostFragment.navController.graph = graph
                            return@setOnItemSelectedListener true
                        }
                    }

                }
                return@setOnItemSelectedListener false
            }
        }
    }
}