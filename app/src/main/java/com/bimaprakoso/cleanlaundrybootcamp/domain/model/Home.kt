package com.bimaprakoso.cleanlaundrybootcamp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class  Home (
    val namaLengkap: String,
    val alamat: String,
    val tanggalLahir: String,
    val noHp: String,
    val username: String,
    val email: String,
    val groupMenu: String,
    val totalLaundry: Int,
    val totalPaid: Int,
    val totalDone: Int,
    val totalTaken: Int,
) {
}