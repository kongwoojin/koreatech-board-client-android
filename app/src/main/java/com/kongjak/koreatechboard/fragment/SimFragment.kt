package com.kongjak.koreatechboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kongjak.koreatechboard.R

class SimFragment : Fragment() {
    lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_sim, container, false)

        val simNotice = BoardFragment()
        val simNoticeBundle = Bundle()
        simNoticeBundle.putString("board", "notice")
        simNoticeBundle.putString("site", "sim")
        simNotice.arguments = simNoticeBundle

        fragment = if (savedInstanceState == null) {
            simNotice
        } else {
            parentFragmentManager.getFragment(savedInstanceState, "fragment")!!
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