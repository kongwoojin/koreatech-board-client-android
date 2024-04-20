package com.kongjak.koreatechboard.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.kongjak.koreatechboard.R

data class KoreatechBoardAppBarAction(
    val icon: ImageVector,
    val action: () -> Unit,
    val contentDescription: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KoreatechBoardAppBar(
    title: String = stringResource(id = R.string.app_name),
    canGoBack: Boolean = false,
    backAction: (() -> Unit)? = null,
    actionList: List<KoreatechBoardAppBarAction> = emptyList(),
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon =
        {
            if (canGoBack) {
                IconButton(onClick = {
                    if (backAction != null) {
                        backAction()
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.content_description_back)
                    )
                }
            }
        },
        actions = {
            actionList.forEach {
                IconButton(onClick = {
                    it.action()
                }) {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.contentDescription
                    )
                }
            }
        }
    )
}