package com.khanhlh.firewarningkt

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.khanhlh.firewarningkt.view.gallery.GalleryFragment
import com.khanhlh.firewarningkt.view.home.HomeFragment
import com.khanhlh.firewarningkt.view.slideshow.SlideshowFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity() {
    val FRAGMENT_HOME = "FRAGMENT_HOME"
    val FRAGMENT_OTHER = "FRAGMENT_OTHER"

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
    }

    private fun initView() {
        setSupportActionBar(toolbar)

        title = "HOME"
        viewFragment(HomeFragment(), FRAGMENT_HOME)
        bottom_nav.setOnNavigationItemSelectedListener(onBottomBarClicked)
    }

    private fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private val onBottomBarClicked =
        BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            val fragment: Fragment
            when (item.itemId) {
                R.id.navigation_home -> {
                    title = "Home"
                    fragment = HomeFragment()
                    viewFragment(fragment, FRAGMENT_HOME)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_plus -> {
                    title = "Plus"
                    fragment = GalleryFragment()
                    viewFragment(fragment, FRAGMENT_OTHER)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_settings -> {
                    title = "Setting"
                    fragment = SlideshowFragment()
                    viewFragment(fragment, FRAGMENT_OTHER)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun viewFragment(fragment: Fragment, name: String) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
        // 1. Know how many fragments there are in the stack
        val count: Int = fragmentManager.backStackEntryCount
        // 2. If the fragment is **not** "home type", save it to the stack
        if (name == FRAGMENT_OTHER) {
            fragmentTransaction.addToBackStack(name)
        }
        // Commit !
        fragmentTransaction.commit()
        // 3. After the commit, if the fragment is not an "home type" the back stack is changed, triggering the
        // OnBackStackChanged callback
        fragmentManager.addOnBackStackChangedListener(object :
            FragmentManager.OnBackStackChangedListener {
            override fun onBackStackChanged() {
                // If the stack decreases it means I clicked the back button
                if (fragmentManager.backStackEntryCount <= count) {
                    // pop all the fragment and remove the listener
                    fragmentManager.popBackStack(FRAGMENT_OTHER, POP_BACK_STACK_INCLUSIVE)
                    fragmentManager.removeOnBackStackChangedListener(this)
                    // set the home button selected
                    bottom_nav.menu.getItem(0).isChecked = true
                }
            }
        })
    }

}
