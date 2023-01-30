package com.example.recyclerviewtest

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DatosEstacion(
    val Operadora: String?,
    val Estacion: String?,
    val Lineas: String?,
    val Enlaces: String?,
    val zonaATMBarcelona: String?,
    val Inauguracion: String?,
    val DescripcionDeLaEstacion: String?,
    val Foto: String?
): Parcelable