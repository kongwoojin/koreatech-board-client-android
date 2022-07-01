package com.kongjak.koreatechcse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kongjak.koreatechcse.databinding.ActivityMainBinding
import com.kongjak.koreatechcse.fragment.FreeBoardFragment
import com.kongjak.koreatechcse.fragment.JobBoardFragment
import com.kongjak.koreatechcse.fragment.NoticeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val noticeFragment = NoticeFragment()
        val freeBoardFragment = FreeBoardFragment()
        val jobBoardFragment = JobBoardFragment()

        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.main_frame_layout, noticeFragment)
            .commit()

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_notice -> {
                    supportFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_frame_layout, noticeFragment)
                        .commit()
                    true
                }
                R.id.navigation_free_board -> {
                    supportFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_frame_layout, freeBoardFragment)
                        .commit()
                    true
                }
                R.id.navigation_job_board -> {
                    supportFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_frame_layout, jobBoardFragment)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}