package com.kongjak.koreatechboard.service

import com.google.firebase.messaging.FirebaseMessagingService

class FCMService : FirebaseMessagingService() {
    /**
    val insertMultipleNewNoticesUseCase: InsertMultipleNewNoticesUseCase by inject()
    override fun onMessageReceived(message: RemoteMessage) {
    Log.d("FCM", "!!!!!!")
    if (message.data["new_articles"] != null && message.data["new_articles"]!!.isNotEmpty()) {
    CoroutineScope(Dispatchers.IO).launch {
    runBlocking {
    insertMultipleNewNoticesUseCase(
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
    val intent = Intent(this, MainActivity::class.java).apply {
    putExtra("screen", "notice")
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
     **/
}
