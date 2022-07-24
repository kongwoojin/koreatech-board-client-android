package com.kongjak.koreatechboard.data.model

import com.google.gson.annotations.SerializedName

data class FilesResponse(
    @SerializedName("file_name")
    val fileName: String,
    @SerializedName("file_uri")
    val fileUri: String
)
