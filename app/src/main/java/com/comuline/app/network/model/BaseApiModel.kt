package com.comuline.app.network.model

// TODO: API base
interface BaseApiModel<T> {
    val status: Int
    val data: List<T>
}