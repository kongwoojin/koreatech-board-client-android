package com.kongjak.koreatechboard.util.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import koreatech_board.app.generated.resources.Res
import koreatech_board.app.generated.resources.route_article
import koreatech_board.app.generated.resources.route_board
import koreatech_board.app.generated.resources.route_home
import koreatech_board.app.generated.resources.route_licenses
import koreatech_board.app.generated.resources.route_new_notice
import koreatech_board.app.generated.resources.route_search
import koreatech_board.app.generated.resources.route_settings
import org.jetbrains.compose.resources.StringResource

sealed class MainRoute(val name: String, val stringResource: StringResource, val icon: ImageVector) {
    data object Home : MainRoute("Home", Res.string.route_home, Icons.Filled.Home)
    data object Board : MainRoute("Board", Res.string.route_board, Icons.AutoMirrored.Filled.List)
    data object Settings : MainRoute("Settings", Res.string.route_settings, Icons.Filled.Settings)
    data object Article : MainRoute("Article", Res.string.route_article, Icons.AutoMirrored.Filled.List)
    data object Search : MainRoute("Search", Res.string.route_search, Icons.Filled.Search)
    data object Notice : MainRoute("Notice", Res.string.route_new_notice, Icons.Filled.Notifications)
    data object Licenses : MainRoute("Licenses", Res.string.route_licenses, Icons.AutoMirrored.Filled.List)

    companion object {
        fun valueOf(name: String): MainRoute {
            return when (name.lowercase()) {
                Home.name.lowercase() -> Home
                Board.name.lowercase() -> Board
                Settings.name.lowercase() -> Settings
                Article.name.lowercase() -> Article
                Search.name.lowercase() -> Search
                Notice.name.lowercase() -> Notice
                Licenses.name.lowercase() -> Licenses
                else -> Home
            }
        }
    }
}
