package com.bimaprakoso.cleanlaundrybootcamp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val token: String? = null
) {

}