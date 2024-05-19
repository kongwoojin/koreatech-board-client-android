package com.kongjak.koreatechboard.util

expect fun subscribeFirebaseTopic(topic: String): Boolean
expect fun unsubscribeFirebaseTopic(topic: String): Boolean