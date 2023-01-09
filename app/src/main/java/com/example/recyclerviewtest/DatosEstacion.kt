package com.example.recyclerviewtest

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DatosEstacion(
    val operadora: String?,
    val estacion: String?,
    val lineas: String?,
    val enlaces: String?,
    val zonaATMBarcelona: String?,
    val inauguracion: String?,
    val descripcionDeLaEstacion: String?,
    val url_foto: String?
): Parcelable