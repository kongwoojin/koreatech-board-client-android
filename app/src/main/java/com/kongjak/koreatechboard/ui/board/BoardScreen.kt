package com.kongjak.koreatechboard.ui.board

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.activity.ArticleActivity
import com.kongjak.koreatechboard.ui.activity.TopBar
import com.kongjak.koreatechboard.ui.theme.NavigationTheme
import com.kongjak.koreatechboard.ui.viewmodel.BoardViewModel
import com.kongjak.koreatechboard.util.enums.DeptEnums

@Composable
fun BoardMain(openDrawer: () -> Unit, site: DeptEnums) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopBar(onButtonClicked = { openDrawer() })
        },
        bottomBar = { BottomNavigation(navController = navController, site) }
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController = navController, site)
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController, site: DeptEnums) {
    androidx.compose.material.BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        site.navItem.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.painterResource),
                        contentDescription = stringResource(id = item.stringResource),
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                label = { Text(stringResource(id = item.stringResource), fontSize = 9.sp) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Gray,
                selected = currentRoute == item.board,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.board) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.background(NavigationTheme.colors.bottomNavigationBackground)
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, site: DeptEnums) {
    NavHost(navController = navController, startDestination = site.navItem[0].board) {
        site.navItem.forEach { item ->
            composable(item.board) {
                BoardScreen(site = site.name.lowercase(), board = item.board)
            }
        }
    }
}

@Composable
fun BoardScreen(boardViewModel: BoardViewModel = hiltViewModel(), site: String, board: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val context = LocalContext.current
        val lazyPostList = boardViewModel.getAPI(site, board).collectAsLazyPagingItems()

        LazyColumn {
            items(lazyPostList.itemCount) { index ->
                val boardItem = lazyPostList[index]
                boardItem?.let {
                    BoardItem(
                        modifier = Modifier.fillMaxWidth().padding(16.dp).selectable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            selected = false,
                            onClick = {
                                val intent = Intent(context, ArticleActivity::class.java)
                                intent.putExtra("site", site)
                                intent.putExtra("uuid", it.uuid.toString())
                                context.startActivity(intent)
                            }
                        ),
                        title = it.title!!, writer = it.writer!!, date = it.writeDate!!
                    )
                    Divider(color = Gray, thickness = 0.5.dp)
                }
            }

            lazyPostList.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { CircularProgressIndicator() }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { CircularProgressIndicator() }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val errorMessage =
                            (loadState.refresh as LoadState.Error).error.localizedMessage
                        item { BoardError(errorMessage) }
                    }

                    loadState.append is LoadState.Error -> {
                        val errorMessage =
                            (loadState.append as LoadState.Error).error.localizedMessage
                        item { BoardError(errorMessage) }
                    }
                }
            }
        }
    }
}

@Composable
fun BoardItem(modifier: Modifier, title: String, writer: String, date: String) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (titleView, writerDateView) = createRefs()

        Text(
            text = title,
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier.constrainAs(titleView) {
                top.linkTo(writerDateView.top)
                bottom.linkTo(writerDateView.bottom)
                start.linkTo(parent.start)
                end.linkTo(writerDateView.start)
                width = Dimension.fillToConstraints
            }
        )
        Column(
            modifier = Modifier
                .constrainAs(writerDateView) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(titleView.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(start = 16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = writer,
                textAlign = TextAlign.End,
                fontSize = 14.sp
            )
            Text(
                text = date,
                textAlign = TextAlign.End,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun BoardError(errorMessage: String) {
    Text(
        text = stringResource(R.string.server_down, errorMessage),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}
