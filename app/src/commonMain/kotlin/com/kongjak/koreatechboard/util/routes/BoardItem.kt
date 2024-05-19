package com.kongjak.koreatechboard.util.routes

import koreatech_board.app.generated.resources.Res
import koreatech_board.app.generated.resources.board_bachelor_board
import koreatech_board.app.generated.resources.board_free_board
import koreatech_board.app.generated.resources.board_job_board
import koreatech_board.app.generated.resources.board_lecture_board
import koreatech_board.app.generated.resources.board_notice
import koreatech_board.app.generated.resources.board_pds_board
import koreatech_board.app.generated.resources.board_scholar_board
import koreatech_board.app.generated.resources.ic_bottom_navigation_bachelor
import koreatech_board.app.generated.resources.ic_bottom_navigation_freeboard
import koreatech_board.app.generated.resources.ic_bottom_navigation_jobboard
import koreatech_board.app.generated.resources.ic_bottom_navigation_lecture
import koreatech_board.app.generated.resources.ic_bottom_navigation_notice
import koreatech_board.app.generated.resources.ic_bottom_navigation_pds
import koreatech_board.app.generated.resources.ic_bottom_navigation_scholar
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalResourceApi::class)
sealed class BoardItem(
    val stringResource: StringResource,
    val painterResource: DrawableResource,
    val board: String
) {
    object Notice : BoardItem(Res.string.board_notice, Res.drawable.ic_bottom_navigation_notice, "notice")
    object Free :
        BoardItem(Res.string.board_free_board, Res.drawable.ic_bottom_navigation_freeboard, "free")
    object Job : BoardItem(Res.string.board_job_board, Res.drawable.ic_bottom_navigation_jobboard, "job")
    object Lecture : BoardItem(Res.string.board_lecture_board, Res.drawable.ic_bottom_navigation_lecture, "lecture")
    object Scholar : BoardItem(Res.string.board_scholar_board, Res.drawable.ic_bottom_navigation_scholar, "scholar")
    object Bachelor : BoardItem(Res.string.board_bachelor_board, Res.drawable.ic_bottom_navigation_bachelor, "bachelor")
    object PDS : BoardItem(Res.string.board_pds_board, Res.drawable.ic_bottom_navigation_pds, "pds")

    companion object {
        fun valueOf(name: String): BoardItem {
            return when (name.lowercase()) {
                Notice.board -> Notice
                Free.board -> Free
                Job.board -> Job
                Lecture.board -> Lecture
                Scholar.board -> Scholar
                Bachelor.board -> Bachelor
                PDS.board -> PDS
                else -> Notice
            }
        }
    }
}

val cseBoards = listOf(
    BoardItem.Notice,
    BoardItem.Lecture,
    BoardItem.Job,
    BoardItem.PDS
)

val archBoards = listOf(
    BoardItem.Notice,
    BoardItem.Job,
    BoardItem.Free
)

val ideBoards = listOf(
    BoardItem.Notice,
    BoardItem.Job
)

val mechanicalBoards = listOf(
    BoardItem.Notice,
    BoardItem.Lecture,
    BoardItem.Bachelor,
    BoardItem.Job
)

val mechatronicsBoards = listOf(
    BoardItem.Notice,
    BoardItem.Bachelor,
    BoardItem.Job,
    BoardItem.PDS,
    BoardItem.Free
)

val schoolBoards = listOf(
    BoardItem.Notice,
    BoardItem.Scholar,
    BoardItem.Bachelor
)

val dormBoards = listOf(
    BoardItem.Notice
)

val iteBoards = listOf(
    BoardItem.Notice,
    BoardItem.Job
)

val simBoards = listOf(
    BoardItem.Notice
)

val mseBoards = listOf(
    BoardItem.Notice,
    BoardItem.Bachelor,
    BoardItem.PDS
)

val aceBoards = listOf(
    BoardItem.Notice,
    BoardItem.Bachelor,
    BoardItem.Job,
    BoardItem.PDS
)
