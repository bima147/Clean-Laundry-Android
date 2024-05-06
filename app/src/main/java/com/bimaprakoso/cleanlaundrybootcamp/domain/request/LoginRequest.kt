package com.bimaprakoso.cleanlaundrybootcamp.domain.request

import java.io.Serializable

data class LoginRequest (
    val username: String? = "",
    val password: String? = "",
): Serializable