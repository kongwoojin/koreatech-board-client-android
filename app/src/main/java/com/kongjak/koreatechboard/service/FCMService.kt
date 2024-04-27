package com.kongjak.koreatechboard.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kongjak.koreatechboard.BuildConfig
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.domain.usecase.database.InsertMultipleArticleUseCase
import com.kongjak.koreatechboard.ui.notice.NoticeActivity
import com.kongjak.koreatechboard.util.routes.BoardItem
import com.kongjak.koreatechboard.util.routes.Department
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class FCMService : FirebaseMessagingService() {
    @Inject
    lateinit var insertMultipleArticleUseCase: InsertMultipleArticleUseCase
    override fun onMessageReceived(message: RemoteMessage) {
        if (message.data["new_articles"] != null && message.data["new_articles"]!!.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                runBlocking {
                    insertMultipleArticleUseCase(
                        message.data["new_articles"]?.split(":")?.takeIf { it.isNotEmpty() }
                            ?.map { UUID.fromString(it) } ?: emptyList(),
                        message.data["department"] ?: "school",
                        message.data["board"] ?: "notice"
                    )
                }
                sendNotification(message)
            }
        }
    }

    private fun sendNotification(message: RemoteMessage) {
        val intent = Intent(this, NoticeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val department = Department.valueOf(message.data["department"] ?: "school")
        val board = BoardItem.valueOf(message.data["board"] ?: "notice")

        val channelId = when (department) {
            Department.School -> getString(R.string.school_notification_channel_id)
            Department.Dorm -> getString(R.string.dorm_notification_channel_id)
            else -> getString(R.string.department_notification_channel_id)
        }

        val channelName = when (department) {
            Department.School -> getString(R.string.school_notification_channel_name)
            Department.Dorm -> getString(R.string.dorm_notification_channel_name)
            else -> getString(R.string.department_notification_channel_name)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.new_notice_notification_title))
            .setContentText(
                getString(
                    R.string.new_notice_notification_content,
                    getString(department.stringResource),
                    getString(board.stringResource)
                )
            )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }

        manager.notify(Integer.parseInt(channelId), notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        if (BuildConfig.BUILD_TYPE == "debug") {
            Log.d("FCM", "Refreshed token: $token")
        }
    }
}
