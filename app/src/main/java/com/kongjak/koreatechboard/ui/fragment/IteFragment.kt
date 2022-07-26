package com.kongjak.koreatechboard.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kongjak.koreatechboard.R

class IteFragment : Fragment() {
    lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_ite, container, false)

        val iteNotice = BoardFragment()
        val iteNoticeBundle = Bundle()
        iteNoticeBundle.putString("board", "notice")
        iteNoticeBundle.putString("site", "ite")
        iteNotice.arguments = iteNoticeBundle

         if (savedInstanceState == null) {
            if (!this::fragment.isInitialized) {
                fragment = iteNotice
            }
         } else {
             fragment =parentFragmentManager.getFragment(savedInstanceState, "fragment")!!
        }
        loadFragment()

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