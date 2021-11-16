package com.pmdm.flixnet.objetos

import java.io.Serializable

data class Usuario(val nombre: String,
                   val email: String,
                   val edad: Int): Serializable
