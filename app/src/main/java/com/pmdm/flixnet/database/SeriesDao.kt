package com.pmdm.flixnet.database

import androidx.room.*
import com.pmdm.flixnet.entidades.Serie
import com.pmdm.flixnet.entidades.Usuario

/**
 * Antonio José Sánchez
 * Programación Multimedia y de Dispositivos Móviles
 * curso 2021|22
 */

@Dao
interface SeriesDao
{
    /**
     * Usuarios
     */
    @Query("SELECT COUNT(*) FROM usuario ;")
    fun countUsers(): Int

    @Query("SELECT * FROM usuario WHERE uid = :uid ;")
    fun getUserById(uid: String): Usuario

    @Insert
    fun insertUser(vararg usuario: Usuario)


    /**
     * Series
     */
    @Query("SELECT COUNT(*) FROM serie ;")
    fun countSeries(): Int

    @Query("SELECT * FROM serie ;")
    fun getShows(): MutableList<Serie>

    @Query("SELECT * FROM serie WHERE idSer = :id")
    fun getShowById(id: Int): Serie

    @Insert
    fun insertShow(vararg serie: Serie)

    @Update
    fun updateShow(vararg serie: Serie)

    @Delete
    fun deleteShow(vararg serie: Serie)


}