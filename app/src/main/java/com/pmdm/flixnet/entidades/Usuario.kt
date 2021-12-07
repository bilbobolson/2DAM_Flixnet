package com.pmdm.flixnet.entidades

/**
 * Antonio José Sánchez
 * Programación Multimedia y de Dispositivos Móviles
 * curso 2021|22
 */

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Usuario(@PrimaryKey val uid: String,
                   @ColumnInfo val nombre: String,
                   @ColumnInfo val apellidos: String,
                   @ColumnInfo val foto: String? = null): Serializable
