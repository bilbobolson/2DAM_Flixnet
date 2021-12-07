package com.pmdm.flixnet.database

/**
 * Antonio José Sánchez
 * Programación Multimedia y de Dispositivos Móviles
 * curso 2021|22
 */

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pmdm.flixnet.entidades.Serie
import com.pmdm.flixnet.entidades.Usuario
import com.pmdm.flixnet.entidades.UsuarioSerie

@Database(entities = [Usuario::class, Serie::class, UsuarioSerie::class], version = 3)
abstract class SeriesDatabase: RoomDatabase()
{
    abstract fun getSeriesDao(): SeriesDao

    companion object
    {
        private var instancia: SeriesDatabase? = null

        @Synchronized
        fun getSeriesDatabase(contexto: Context): SeriesDatabase?
        {
            if (instancia === null)
                instancia = Room.databaseBuilder(contexto, SeriesDatabase::class.java, "seriesdb")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

            return instancia
        }
    }
}