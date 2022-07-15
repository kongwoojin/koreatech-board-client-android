package com.kongjak.koreatechboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import com.kongjak.koreatechboard.activity.SettingsActivity
import com.kongjak.koreatechboard.fragment.*

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
        val mechanicalFragment = MechanicalFragment()
        val mechatronicsFragment = MechatronicsFragment()
        val iteFragment = IteFragment()
        val ideFragment = IdeFragment()
        val archFragment = ArchFragment()
        val emcFragment = EmcFragment()
        val simFragment = SimFragment()
        val dormFragment = DormFragment()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        when (prefs.getString("default_board", "school")) {
            "school" -> {
                defaultFragment = schoolFragment
                navView.setCheckedItem(R.id.nav_drawer_school)
            }
            "cse" -> {
                defaultFragment = cseFragment
                navView.setCheckedItem(R.id.nav_drawer_cse)
            }
            "dorm" -> {
                defaultFragment = dormFragment
                navView.setCheckedItem(R.id.nav_drawer_dorm)
            }
            "mechanical" -> {
                defaultFragment = mechanicalFragment
                navView.setCheckedItem(R.id.nav_drawer_cse)
            }
            "mechatronics" -> {
                defaultFragment = mechatronicsFragment
                navView.setCheckedItem(R.id.nav_drawer_cse)
            }
            "ite" -> {
                defaultFragment = iteFragment
                navView.setCheckedItem(R.id.nav_drawer_cse)
            }
            "ide" -> {
                defaultFragment = ideFragment
                navView.setCheckedItem(R.id.nav_drawer_cse)
            }
            "arch" -> {
                defaultFragment = archFragment
                navView.setCheckedItem(R.id.nav_drawer_cse)
            }
            "emc" -> {
                defaultFragment = emcFragment
                navView.setCheckedItem(R.id.nav_drawer_cse)
            }
            "sim" -> {
                defaultFragment = simFragment
                navView.setCheckedItem(R.id.nav_drawer_cse)
            }
            else -> {
                defaultFragment = schoolFragment
                navView.setCheckedItem(R.id.nav_drawer_school)
            }
        }

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
                    loadFragment()
                }
                R.id.nav_drawer_dorm -> {
                    fragment = dormFragment
                    loadFragment()
                }
                R.id.nav_drawer_cse -> {
                    fragment = cseFragment
                    loadFragment()
                }
                R.id.nav_drawer_mechanical -> {
                    fragment = mechanicalFragment
                    loadFragment()
                }
                R.id.nav_drawer_mechatronics -> {
                    fragment = mechatronicsFragment
                    loadFragment()
                }
                R.id.nav_drawer_ite -> {
                    fragment = iteFragment
                    loadFragment()
                }
                R.id.nav_drawer_ide -> {
                    fragment = ideFragment
                    loadFragment()
                }
                R.id.nav_drawer_arch -> {
                    fragment = archFragment
                    loadFragment()
                }
                R.id.nav_drawer_emc -> {
                    fragment = emcFragment
                    loadFragment()
                }
                R.id.nav_drawer_sim -> {
                    fragment = simFragment
                    loadFragment()
                }
                R.id.nav_drawer_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                }
            }
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