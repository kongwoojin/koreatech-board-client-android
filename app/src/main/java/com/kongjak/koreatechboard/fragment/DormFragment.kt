package com.kongjak.koreatechboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kongjak.koreatechboard.R

class DormFragment : Fragment() {
    lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_dorm, container, false)

        val bottomNavView: BottomNavigationView = rootView.findViewById(R.id.bottom_nav_view_dorm)

        val dormNotice = DormBoardFragment()
        val dormNoticeBundle = Bundle()
        dormNoticeBundle.putString("board", "notice")
        dormNotice.arguments = dormNoticeBundle

        val dormFreeBoard = DormBoardFragment()
        val dormFreeBoardBundle = Bundle()
        dormFreeBoardBundle.putString("board", "free")
        dormFreeBoard.arguments = dormFreeBoardBundle

        fragment = if (savedInstanceState == null) {
            dormNotice
        } else {
            parentFragmentManager.getFragment(savedInstanceState, "fragment")!!
        }
        loadFragment()

        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dorm_notice -> {
                    fragment = dormNotice
                    loadFragment()
                    true
                }
                R.id.navigation_dorm_free_board -> {
                    fragment = dormFreeBoard
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