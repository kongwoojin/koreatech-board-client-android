package com.kongjak.koreatechboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import com.kongjak.koreatechboard.fragment.CseFragment
import com.kongjak.koreatechboard.fragment.DormFragment
import com.kongjak.koreatechboard.fragment.SchoolFragment
import com.kongjak.koreatechboard.fragment.SettingsFragment

class MainActivity : AppCompatActivity() {

    lateinit var defaultFragment: Fragment
    lateinit var fragment: Fragment
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val navView: NavigationView = findViewById(R.id.nav_view)
        drawerLayout = findViewById(R.id.drawer_layout)

        setSupportActionBar(toolbar)

        supportActionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_nav_menu)
        }

        toolbar.setNavigationOnClickListener {
            drawerLayout.open()
        }
        val schoolFragment = SchoolFragment()
        val cseFragment = CseFragment()
        val dormFragment = DormFragment()
        val settingsFragment = SettingsFragment()

        fragment = if (savedInstanceState == null) {
            defaultFragment
        } else {
            supportFragmentManager.getFragment(savedInstanceState, "fragment")!!
        }
        loadFragment()

        navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.close()
            when (menuItem.itemId) {
                R.id.nav_drawer_school -> {
                    fragment = schoolFragment
                }
                R.id.nav_drawer_cse -> {
                    fragment = cseFragment
                }
                R.id.nav_drawer_dorm -> {
                    fragment = dormFragment
                }
                R.id.nav_drawer_settings -> {
                    fragment = settingsFragment
                }
            }
            loadFragment()
            true
        }
    }

    private fun loadFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame_layout, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, "fragment", fragment)
    }
}