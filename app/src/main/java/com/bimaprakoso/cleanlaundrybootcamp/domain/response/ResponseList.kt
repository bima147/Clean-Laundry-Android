package com.bimaprakoso.cleanlaundrybootcamp.domain.response

import java.io.Serializable

data class ResponseList<T> (
    val data: ListItemResponse<T>?,
    val success: Boolean? = false,
    val message: String? = "",
    val status: Int? = 0,
    val timestamp: Long = 0,
): Serializable