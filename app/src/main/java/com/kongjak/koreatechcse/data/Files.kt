package com.kongjak.koreatechcse.data

import com.google.gson.annotations.SerializedName

data class Files(
    @SerializedName("file_name")
    val fileName: String,
    @SerializedName("file_uri")
    val fileUri: String
)
