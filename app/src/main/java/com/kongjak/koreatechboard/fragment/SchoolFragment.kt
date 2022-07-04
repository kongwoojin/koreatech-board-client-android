package com.kongjak.koreatechboard.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kongjak.koreatechboard.R

class SchoolFragment : Fragment() {
    lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_school, container, false)

        val bottomNavView: BottomNavigationView = rootView.findViewById(R.id.bottom_nav_view_school)

        val schoolNotice = BoardFragment()
        val schoolNoticeBundle = Bundle()
        schoolNoticeBundle.putString("board", "notice")
        schoolNoticeBundle.putString("site", "school")
        schoolNotice.arguments = schoolNoticeBundle

        val schoolScholar = BoardFragment()
        val schoolScholarBundle = Bundle()
        schoolScholarBundle.putString("board", "scholar")
        schoolScholarBundle.putString("site", "school")
        schoolScholar.arguments = schoolScholarBundle

        val schoolBachelor = BoardFragment()
        val schoolBachelorBundle = Bundle()
        schoolBachelorBundle.putString("board", "bachelor")
        schoolBachelorBundle.putString("site", "school")
        schoolBachelor.arguments = schoolBachelorBundle

        val schoolCovid19 = BoardFragment()
        val schoolCovid19Bundle = Bundle()
        schoolCovid19Bundle.putString("board", "covid19")
        schoolCovid19Bundle.putString("site", "school")
        schoolCovid19.arguments = schoolCovid19Bundle

        fragment = if (savedInstanceState == null) {
            schoolNotice
        } else {
            parentFragmentManager.getFragment(savedInstanceState, "fragment")!!
        }
        loadFragment()

        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_school_notice -> {
                    fragment = schoolNotice
                    loadFragment()
                    true
                }
                R.id.navigation_school_scholar -> {
                    fragment = schoolScholar
                    loadFragment()
                    true
                }
                R.id.navigation_school_bachelor -> {
                    fragment = schoolBachelor
                    loadFragment()
                    true
                }
                R.id.navigation_school_covid19 -> {
                    fragment = schoolCovid19
                    loadFragment()
                    true
                }
                else -> false
            }

        }

        return rootView
    }

    private fun loadFragment() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.notice_frame_layout, fragment)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        parentFragmentManager.putFragment(outState, "fragment", fragment)
    }
}