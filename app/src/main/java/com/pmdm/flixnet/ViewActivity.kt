package com.pmdm.flixnet

/**
 * Antonio José Sánchez
 * Programación Multimedia y de Dispositivos Móviles
 * curso 2021|22
 */

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.pmdm.flixnet.database.SeriesDatabase
import com.pmdm.flixnet.databinding.ActivityViewBinding

class ViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        // vinculación de vistas
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // recuperamos información de la intención
        val id: Int = intent.extras?.getInt("_id") as Int

        // buscamos la serie en la base de datos y mostramos la información
        val serie = SeriesDatabase.getSeriesDatabase(this)?.getSeriesDao()?.getShowById(id)

        serie?.let {

            binding.apply {

                // mostramos el título
                infoTitulo.text = serie.titulo

                // mostramos la puntuación
                infoPuntos.text = serie.puntos.toString()

                // mostramos el poster
                Glide.with(binding.root).load(serie.poster).into(infoPoster)

                //
                btnInfoVolver.setOnClickListener { finish() }
            }
        }
    }
}