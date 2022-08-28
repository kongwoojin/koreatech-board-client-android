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
class ArchFragment : Fragment() {
    lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_arch, container, false)

        val bottomNavView: BottomNavigationView = rootView.findViewById(R.id.bottom_nav_view)

        val archNotice = BoardFragment()
        val archNoticeBundle = Bundle()
        archNoticeBundle.putString("board", "notice")
        archNoticeBundle.putString("site", "arch")
        archNotice.arguments = archNoticeBundle

        val archFreeBoard = BoardFragment()
        val archFreeBoardBundle = Bundle()
        archFreeBoardBundle.putString("board", "free")
        archFreeBoardBundle.putString("site", "arch")
        archFreeBoard.arguments = archFreeBoardBundle

        if (savedInstanceState == null) {
            if (!this::fragment.isInitialized) {
                fragment = archNotice
            }
        } else {
            fragment = parentFragmentManager.getFragment(savedInstanceState, "fragment")!!
        }
        loadFragment()

        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_arch_ide_notice -> {
                    fragment = archNotice
                    loadFragment()
                    true
                }
                R.id.navigation_arch_ide_free_board -> {
                    fragment = archFreeBoard
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