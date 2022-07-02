package com.kongjak.koreatechcse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kongjak.koreatechcse.databinding.ActivityMainBinding
import com.kongjak.koreatechcse.fragment.FreeBoardFragment
import com.kongjak.koreatechcse.fragment.JobBoardFragment
import com.kongjak.koreatechcse.fragment.NoticeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val noticeFragment = NoticeFragment()
        val freeBoardFragment = FreeBoardFragment()
        val jobBoardFragment = JobBoardFragment()

        fragment = if (savedInstanceState == null) {
            noticeFragment
        } else {
            supportFragmentManager.getFragment(savedInstanceState, "fragment")!!
        }
        loadFragment()

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_notice -> {
                    fragment = noticeFragment
                    loadFragment()
                    true
                }
                R.id.navigation_free_board -> {
                    fragment = freeBoardFragment
                    loadFragment()
                    true
                }
                R.id.navigation_job_board -> {
                    fragment = jobBoardFragment
                    loadFragment()
                    true
                }
                else -> false
            }

        }
    }

    private fun loadFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame_layout, fragment)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, "fragment", fragment)
    }
}