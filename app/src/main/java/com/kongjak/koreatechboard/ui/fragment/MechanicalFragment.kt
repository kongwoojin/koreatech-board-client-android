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
class MechanicalFragment : Fragment() {
    lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_mechanical, container, false)

        val bottomNavView: BottomNavigationView = rootView.findViewById(R.id.bottom_nav_view)

        val mechanicalNotice = BoardFragment()
        val mechanicalNoticeBundle = Bundle()
        mechanicalNoticeBundle.putString("board", "notice")
        mechanicalNoticeBundle.putString("site", "mechanical")
        mechanicalNotice.arguments = mechanicalNoticeBundle

        val mechanicalLecture = BoardFragment()
        val mechanicalLectureBundle = Bundle()
        mechanicalLectureBundle.putString("board", "lecture")
        mechanicalLectureBundle.putString("site", "mechanical")
        mechanicalLecture.arguments = mechanicalLectureBundle

        val mechanicalFreeBoard = BoardFragment()
        val mechanicalFreeBoardBundle = Bundle()
        mechanicalFreeBoardBundle.putString("board", "free")
        mechanicalFreeBoardBundle.putString("site", "mechanical")
        mechanicalFreeBoard.arguments = mechanicalFreeBoardBundle

        if (savedInstanceState == null) {
            if (!this::fragment.isInitialized) {
                fragment = mechanicalNotice
            }
        } else {
            fragment = parentFragmentManager.getFragment(savedInstanceState, "fragment")!!
        }
        loadFragment()

        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_mechanical_notice -> {
                    fragment = mechanicalNotice
                    loadFragment()
                    true
                }
                R.id.navigation_mechanical_lecture -> {
                    fragment = mechanicalLecture
                    loadFragment()
                    true
                }
                R.id.navigation_mechanical_free_board -> {
                    fragment = mechanicalFreeBoard
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