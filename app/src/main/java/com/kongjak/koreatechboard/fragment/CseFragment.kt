package com.kongjak.koreatechboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kongjak.koreatechboard.R

class CseFragment : Fragment() {
    lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_cse, container, false)

        val bottomNavView: BottomNavigationView = rootView.findViewById(R.id.bottom_nav_view_cse)

        val cseNotice = CseBoardFragment()
        val cseNoticeBundle = Bundle()
        cseNoticeBundle.putString("board", "notice")
        cseNotice.arguments = cseNoticeBundle

        val cseFreeBoard = CseBoardFragment()
        val cseFreeBoardBundle = Bundle()
        cseFreeBoardBundle.putString("board", "free")
        cseFreeBoard.arguments = cseFreeBoardBundle

        val cseJobBoard = CseBoardFragment()
        val cseJobBoardBundle = Bundle()
        cseJobBoardBundle.putString("board", "job")
        cseJobBoard.arguments = cseJobBoardBundle

        fragment = if (savedInstanceState == null) {
            cseNotice
        } else {
            parentFragmentManager.getFragment(savedInstanceState, "fragment")!!
        }
        loadFragment()

        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_cse_notice -> {
                    fragment = cseNotice
                    loadFragment()
                    true
                }
                R.id.navigation_cse_free_board -> {
                    fragment = cseFreeBoard
                    loadFragment()
                    true
                }
                R.id.navigation_cse_job_board -> {
                    fragment = cseJobBoard
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