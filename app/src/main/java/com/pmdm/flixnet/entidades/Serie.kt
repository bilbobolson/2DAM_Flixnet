package com.pmdm.flixnet.entidades

/**
 * Antonio José Sánchez
 * Programación Multimedia y de Dispositivos Móviles
 * curso 2021|22
 */

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Serie(
    @PrimaryKey(autoGenerate = true) val idSer: Int,
    @ColumnInfo val titulo: String,
    @ColumnInfo var puntos: Float,
    @ColumnInfo val poster: String)

