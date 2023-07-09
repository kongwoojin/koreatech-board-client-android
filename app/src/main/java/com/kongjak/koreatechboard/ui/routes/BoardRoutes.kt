package com.kongjak.koreatechboard.ui.routes

import com.kongjak.koreatechboard.R

sealed class BoardItem(
    val stringResource: Int,
    val painterResource: Int,
    val board: String
) { // board is route
    object Notice : BoardItem(R.string.item_notice, R.drawable.ic_bottom_navigation_notice, "notice")
    object Free :
        BoardItem(R.string.item_free_board, R.drawable.ic_bottom_navigation_freeboard, "free")
    object Job : BoardItem(R.string.item_job_board, R.drawable.ic_bottom_navigation_jobboard, "job")
    object Lecture : BoardItem(R.string.item_lecture_board, R.drawable.ic_bottom_navigation_lecture, "lecture")
    object Scholar : BoardItem(R.string.item_scholar_board, R.drawable.ic_bottom_navigation_scholar, "scholar")
    object Bachelor : BoardItem(R.string.item_bachelor_board, R.drawable.ic_bottom_navigation_bachelor, "bachelor")
    object Covid19 : BoardItem(R.string.item_covid19_board, R.drawable.ic_bottom_navigation_covid, "covid19")
    object PDS : BoardItem(R.string.item_pds_board, R.drawable.ic_bottom_navigation_pds, "pds")
}

val cseBoards = listOf(
    BoardItem.Notice,
    BoardItem.Free,
    BoardItem.Job,
    BoardItem.PDS
)

val archBoards = listOf(
    BoardItem.Notice,
    BoardItem.Free
)

val ideBoards = listOf(
    BoardItem.Notice,
    BoardItem.Free
)

val mechanicalBoards = listOf(
    BoardItem.Notice,
    BoardItem.Free
)

val mechatronicsBoards = listOf(
    BoardItem.Notice,
    BoardItem.Lecture,
    BoardItem.Bachelor,
    BoardItem.Job,
    BoardItem.Free
)

val schoolBoards = listOf(
    BoardItem.Notice,
    BoardItem.Scholar,
    BoardItem.Bachelor,
    BoardItem.Covid19
)

val dormBoards = listOf(
    BoardItem.Notice
)

val iteBoards = listOf(
    BoardItem.Notice
)

val simBoards = listOf(
    BoardItem.Notice
)

val emcBoards = listOf(
    BoardItem.Notice
)
