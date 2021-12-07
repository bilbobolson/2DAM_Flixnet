package com.pmdm.flixnet.entidades

/**
 * Antonio José Sánchez
 * Programación Multimedia y de Dispositivos Móviles
 * curso 2021|22
 */

import androidx.room.Entity

@Entity(primaryKeys = ["uid", "idSer"])
data class UsuarioSerie( val uid: String, val idSer: Int )
