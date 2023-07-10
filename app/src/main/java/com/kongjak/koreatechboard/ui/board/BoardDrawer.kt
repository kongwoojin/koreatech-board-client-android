package com.kongjak.koreatechboard.ui.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.routes.deptList

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit
) {
    val selectedMenu = remember { mutableStateOf(deptList[0]) }
    Column(
        modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .fillMaxWidth()
                .padding(top = 48.dp, start = 8.dp, end = 8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "App icon"
            )
        }
        Divider(color = Color.Transparent, thickness = 8.dp)

        deptList.forEach { item ->
            val backgroundColor =
                if (item == selectedMenu.value) MaterialTheme.colors.primary.copy(alpha = 0.1f) else Color.Transparent
            Box(
                modifier = Modifier
                    .selectable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        selected = false,
                        onClick = {
                            onDestinationClicked(item.dept.name)
                            selectedMenu.value = item
                        }
                    )
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 4.dp)
                    .background(backgroundColor)

            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                    text = stringResource(id = item.stringResource),
                    fontSize = 14.sp
                )
            }
        }
    }
}
