package com.bimaprakoso.cleanlaundrybootcamp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction (
    val transactionUUID: String? = "",
    val done: Boolean? = false,
    val taken: Boolean? = false,
    val paid: Boolean? = false
) {
}