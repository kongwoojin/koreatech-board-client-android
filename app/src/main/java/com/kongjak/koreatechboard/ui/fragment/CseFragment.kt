package com.kongjak.koreatechboard.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kongjak.koreatechboard.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_cse, container, false)

        val navView: BottomNavigationView = rootView.findViewById(R.id.bottom_nav_view)

        val navController = childFragmentManager.findFragmentById(R.id.board_fragment_container_view)?.findNavController()
        navView.setupWithNavController(navController!!)

        return rootView
    }
}
