package com.bimaprakoso.cleanlaundrybootcamp.domain.response

import com.bimaprakoso.cleanlaundrybootcamp.domain.model.User
import java.io.Serializable

data class LoginResponse (
    val token: String? = "",
): Serializable

fun LoginResponse?.mapToUser(): User {
    return User (
        token = this?.token.orEmpty()
    )
}