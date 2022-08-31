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
class CseFragment : Fragment() {
    lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_cse, container, false)

        val bottomNavView: BottomNavigationView = rootView.findViewById(R.id.bottom_nav_view)

        val cseNotice = BoardFragment()
        val cseNoticeBundle = Bundle()
        cseNoticeBundle.putString("board", "notice")
        cseNoticeBundle.putString("site", "cse")
        cseNotice.arguments = cseNoticeBundle

        val cseFreeBoard = BoardFragment()
        val cseFreeBoardBundle = Bundle()
        cseFreeBoardBundle.putString("board", "free")
        cseFreeBoardBundle.putString("site", "cse")
        cseFreeBoard.arguments = cseFreeBoardBundle

        val cseJobBoard = BoardFragment()
        val cseJobBoardBundle = Bundle()
        cseJobBoardBundle.putString("board", "job")
        cseJobBoardBundle.putString("site", "cse")
        cseJobBoard.arguments = cseJobBoardBundle

        val csePdsBoard = BoardFragment()
        val csePdsBoardBundle = Bundle()
        csePdsBoardBundle.putString("board", "pds")
        csePdsBoardBundle.putString("site", "cse")
        csePdsBoard.arguments = csePdsBoardBundle

        if (savedInstanceState == null) {
            if (!this::fragment.isInitialized) {
                fragment = cseNotice
            }
        } else {
            fragment = parentFragmentManager.getFragment(savedInstanceState, "fragment")!!
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
                R.id.navigation_cse_pds -> {
                    fragment = csePdsBoard
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