package com.kongjak.koreatechboard.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kongjak.koreatechboard.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MechatronicsFragment : Fragment() {
    lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_mechatronics, container, false)

        val bottomNavView: BottomNavigationView = rootView.findViewById(R.id.bottom_nav_view)

        val mechatronicsNotice = BoardFragment()
        val mechatronicsNoticeBundle = Bundle()
        mechatronicsNoticeBundle.putString("board", "notice")
        mechatronicsNoticeBundle.putString("site", "mechatronics")
        mechatronicsNotice.arguments = mechatronicsNoticeBundle

        val mechatronicsLecture = BoardFragment()
        val mechatronicsLectureBundle = Bundle()
        mechatronicsLectureBundle.putString("board", "lecture")
        mechatronicsLectureBundle.putString("site", "mechatronics")
        mechatronicsLecture.arguments = mechatronicsLectureBundle

        val mechatronicsBachelor = BoardFragment()
        val mechatronicsBachelorBundle = Bundle()
        mechatronicsBachelorBundle.putString("board", "bachelor")
        mechatronicsBachelorBundle.putString("site", "mechatronics")
        mechatronicsBachelor.arguments = mechatronicsBachelorBundle

        val mechatronicsJobBoard = BoardFragment()
        val mechatronicsJobBoardBundle = Bundle()
        mechatronicsJobBoardBundle.putString("board", "job")
        mechatronicsJobBoardBundle.putString("site", "mechatronics")
        mechatronicsJobBoard.arguments = mechatronicsJobBoardBundle

        val mechatronicsFreeBoard = BoardFragment()
        val mechatronicsFreeBoardBundle = Bundle()
        mechatronicsFreeBoardBundle.putString("board", "free")
        mechatronicsFreeBoardBundle.putString("site", "mechatronics")
        mechatronicsFreeBoard.arguments = mechatronicsFreeBoardBundle

        if (savedInstanceState == null) {
            if (!this::fragment.isInitialized) {
                fragment = mechatronicsNotice
            }
        } else {
            fragment = parentFragmentManager.getFragment(savedInstanceState, "fragment")!!
        }
        loadFragment()

        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_mechatronics_notice -> {
                    fragment = mechatronicsNotice
                    loadFragment()
                    true
                }
                R.id.navigation_mechatronics_lecture -> {
                    fragment = mechatronicsLecture
                    loadFragment()
                    true
                }
                R.id.navigation_mechatronics_bachelor -> {
                    fragment = mechatronicsBachelor
                    loadFragment()
                    true
                }
                R.id.navigation_mechatronics_job_board -> {
                    fragment = mechatronicsJobBoard
                    loadFragment()
                    true
                }
                R.id.navigation_mechatronics_free_board -> {
                    fragment = mechatronicsFreeBoard
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
