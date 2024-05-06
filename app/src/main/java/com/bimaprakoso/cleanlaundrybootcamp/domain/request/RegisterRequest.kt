package com.bimaprakoso.cleanlaundrybootcamp.domain.request

import java.io.Serializable

data class RegisterRequest(
    val namaLengkap: String? = "",
    val alamat: String? = "",
    val tanggalLahir: String? = "",
    val noHp: String? = "",
    val username: String? = "",
    val email: String? = "",
    val password: String? = "",
): Serializable
