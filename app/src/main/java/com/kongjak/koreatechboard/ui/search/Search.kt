package com.kongjak.koreatechboard.ui.search

import android.content.Intent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.activity.ArticleActivity
import com.kongjak.koreatechboard.ui.board.BoardError
import com.kongjak.koreatechboard.ui.board.BoardItem
import com.kongjak.koreatechboard.util.findActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(site: String, board: String, title: String) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.search_result))
                },
                navigationIcon =
                {
                    IconButton(onClick = {
                        context.findActivity().finish()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { contentPadding ->
            SearchContent(
                contentPadding = contentPadding,
                site = site,
                board = board,
                title = title
            )
        }
    )
}

@Composable
fun SearchContent(
    searchViewModel: SearchViewModel = hiltViewModel(),
    contentPadding: PaddingValues,
    site: String,
    board: String,
    title: String
) {
    val context = LocalContext.current

    val lazyPostList =
        searchViewModel.getAPI(site, board, title).collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(lazyPostList.itemCount) { index ->
            val boardItem = lazyPostList[index]
            boardItem?.let {
                BoardItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .selectable(
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
                    title = it.title, writer = it.writer, date = it.writeDate
                )
                HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
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

                loadState.refresh is LoadState.NotLoading -> {
                    if (lazyPostList.itemCount == 0) {
                        item { Text(text = stringResource(id = R.string.search_no_result)) }
                    }
                }
            }
        }
    }
}
