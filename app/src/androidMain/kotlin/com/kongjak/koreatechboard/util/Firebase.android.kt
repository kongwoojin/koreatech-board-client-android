package com.kongjak.koreatechboard.util

import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

actual fun subscribeFirebaseTopic(topic: String): Boolean {
    var isSuccess = true

    val newTopic = if (true) {
        "development_$topic"
    } else {
        topic
    }

    Firebase.messaging.subscribeToTopic(newTopic)
        .addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                isSuccess = false
            }
        }


    return isSuccess
}

actual fun unsubscribeFirebaseTopic(topic: String): Boolean {
    var isSuccess = true

    val newTopic = if (true) {
        "development_$topic"
    } else {
        topic
    }

    Firebase.messaging.unsubscribeFromTopic(newTopic)
        .addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                isSuccess = false
            }
        }

    return isSuccess
}