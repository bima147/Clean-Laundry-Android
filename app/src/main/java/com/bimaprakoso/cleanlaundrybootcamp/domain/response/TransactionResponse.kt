package com.bimaprakoso.cleanlaundrybootcamp.domain.response

import com.bimaprakoso.cleanlaundrybootcamp.domain.model.Transaction

data class TransactionResponse(
    val transactionUUID: String? = "",
    val done: Boolean? = false,
    val taken: Boolean? = false,
    val paid: Boolean? = false
) {
}
