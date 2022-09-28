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
class IdeFragment : Fragment() {
    lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_ide, container, false)

        val bottomNavView: BottomNavigationView = rootView.findViewById(R.id.bottom_nav_view)

        val ideNotice = BoardFragment()
        val ideNoticeBundle = Bundle()
        ideNoticeBundle.putString("board", "notice")
        ideNoticeBundle.putString("site", "ide")
        ideNotice.arguments = ideNoticeBundle

        val ideFreeBoard = BoardFragment()
        val ideFreeBoardBundle = Bundle()
        ideFreeBoardBundle.putString("board", "free")
        ideFreeBoardBundle.putString("site", "ide")
        ideFreeBoard.arguments = ideFreeBoardBundle

        if (savedInstanceState == null) {
            if (!this::fragment.isInitialized) {
                fragment = ideNotice
            }
        } else {
            fragment = parentFragmentManager.getFragment(savedInstanceState, "fragment")!!
        }
        loadFragment()

        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_arch_ide_notice -> {
                    fragment = ideNotice
                    loadFragment()
                    true
                }
                R.id.navigation_arch_ide_free_board -> {
                    fragment = ideFreeBoard
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
