package com.kongjak.koreatechboard.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.kongjak.koreatechboard.R

sealed class BottomNavigationItem(val name: String, val stringResource: Int, val icon: ImageVector) {
    object Home : BottomNavigationItem("Home", R.string.bottom_nav_bar_home, Icons.Filled.Home)
    object Board : BottomNavigationItem("Board", R.string.bottom_nav_bar_board, Icons.Filled.List)
    object Settings : BottomNavigationItem("Settings", R.string.bottom_nav_bar_settings, Icons.Filled.Settings)

    companion object {
        fun valueOf(name: String): BottomNavigationItem {
            return when (name.lowercase()) {
                Home.name.lowercase() -> Home
                Board.name.lowercase() -> Board
                Settings.name.lowercase() -> Settings
                else -> Home
            }
        }
    }
}
