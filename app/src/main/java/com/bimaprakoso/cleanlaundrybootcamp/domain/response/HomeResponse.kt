package com.bimaprakoso.cleanlaundrybootcamp.domain.response

import com.bimaprakoso.cleanlaundrybootcamp.domain.model.Home

data class HomeResponse(
    val namaLengkap: String? = "",
    val alamat: String? = "",
    val tanggalLahir: String? = "",
    val noHp: String? = "",
    val username: String? = "",
    val email: String? = "",
    val groupMenu: String? = "",
    val totalLaundry: Int? = 0,
    val totalPaid: Int? = 0,
    val totalDone: Int? = 0,
    val totalTaken: Int? = 0,
)

fun HomeResponse?.mapToHome(): Home {
    return Home (
        namaLengkap = this?.namaLengkap.orEmpty(),
        alamat = this?.alamat.orEmpty(),
        tanggalLahir = this?.tanggalLahir.orEmpty(),
        noHp = this?.noHp.orEmpty(),
        username = this?.username.orEmpty(),
        email = this?.email.orEmpty(),
        groupMenu = this?.groupMenu.orEmpty(),
        totalLaundry = this?.totalLaundry?.toInt() ?: 0,
        totalPaid = this?.totalPaid?.toInt() ?: 0,
        totalDone = this?.totalDone?.toInt() ?: 0,
        totalTaken = this?.totalTaken?.toInt() ?: 0
    )
}