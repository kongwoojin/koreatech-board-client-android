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
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.domain.usecase.database.InsertMultipleArticleUseCase
import com.kongjak.koreatechboard.ui.main.MainActivity
import com.kongjak.koreatechboard.util.routes.Department
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class FCMService : FirebaseMessagingService() {
    @Inject
    lateinit var insertMultipleArticleUseCase: InsertMultipleArticleUseCase
    override fun onMessageReceived(message: RemoteMessage) {
        if (message.data["new_articles"] != null && message.data["new_articles"]!!.isNotEmpty()) {

            CoroutineScope(Dispatchers.IO).launch {
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

    private fun sendNotification(message: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("screen", message.data["screen"] ?: "board")
            putExtra("department", message.data["department"])
            putExtra("openedFromNotification", true)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId = this.getString(R.string.new_notice_notification_channel_id)

        val department = Department.valueOf(message.data["department"] ?: "school")

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.new_notice_notification_title))
            .setContentText(
                getString(
                    R.string.new_notice_notification_content,
                    getString(department.stringResource)
                )
            )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                this.getString(R.string.new_notice_notification_channel),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }

        manager.notify(0, notificationBuilder.build())
    }
}
