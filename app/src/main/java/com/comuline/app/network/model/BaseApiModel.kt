package com.comuline.app.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseApiModel<T>(
    @Json(name = "metadata")
    val metadata: Metadata,

    @Json(name = "data")
    val data: T
)

@JsonClass(generateAdapter = true)
data class Metadata(
    @Json(name = "success")
    val success: Boolean?,
    @Json(name = "message")
    val message: String?
)
