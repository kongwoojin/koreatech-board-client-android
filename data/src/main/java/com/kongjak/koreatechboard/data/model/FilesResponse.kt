package com.kongjak.koreatechboard.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilesResponse(
    @SerialName("file_name")
    val fileName: String,
    @SerialName("file_url")
    val fileUrl: String
)
