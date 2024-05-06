package com.bimaprakoso.cleanlaundrybootcamp.domain.response

data class ListItemResponse<T>(
    val totalItems: Int? = 0,
    val numberOfElements: Int? = 0,
    val componentFilter: List<String>? = emptyList(),
    val totalPages: Int? = 0,
    val sort: String? = "",
    val filterBy: String? = "",
    val value: String? = "",
    val content: List<T> = emptyList()
)
