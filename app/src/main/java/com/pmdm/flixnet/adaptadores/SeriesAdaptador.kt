package com.pmdm.flixnet.adaptadores

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pmdm.flixnet.R
import com.pmdm.flixnet.databinding.ListaSeriesItemBinding
import com.pmdm.flixnet.entidades.Serie

/**
 * Antonio José Sánchez
 * Programación Multimedia y de Dispositivos Móviles
 * curso 2021|22
 */

class SeriesAdaptador(var datos: MutableList<Serie>): RecyclerView.Adapter<SeriesAdaptador.SeriesContenedor>()
{
     // Definimos el escuchador para la pulsación simple
    var pulsacionSencilla: (Serie) -> Unit = {}
        set(value)
        {
            field = value
        }

    // Definimos el escuchador para el menú contextual
    var pulsacionLarga: (MenuItem, Serie) -> Boolean = { menuItem: MenuItem,
                                                         serie: Serie -> false }
        set(value)
        {
            field = value
        }

    // Definimos el escuchador para la ratingBar
    var pulsacionRating: (RatingBar, Float, Boolean, Serie) -> Unit = {    rb: RatingBar,
                                                                            f: Float,
                                                                            b: Boolean,
                                                                        serie: Serie -> }
        set(value)
        {
            field = value
        }

    /**
     * @return Int
     */
    override fun getItemCount(): Int = datos.size

    /**
     * Crea una contenedor a partir de un layout y lo asocia al RecyclerView
     * @param parent: ViewGroup
     * @param viewType: Int
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesContenedor
    {
        val inflador = LayoutInflater.from(parent.context)
        val binding = ListaSeriesItemBinding.inflate(inflador, parent, false)

        return SeriesContenedor(binding)
    }

    /**
     * Asocia el contenedor a un determinado elemento de la colección de datos
     * @param holder: SeriesContenedor
     * @param position: Int
     */
    override fun onBindViewHolder(holder: SeriesContenedor, position: Int)
    {
        holder.bindSerie(datos[position])
    }

    /** */
    inner class SeriesContenedor(val binding: ListaSeriesItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bindSerie(serie: Serie)
        {
            // mostramos el título de la serie
            binding.serieTitulo.text = serie.titulo

            // mostramos el póster
            Glide.with(binding.root).load(serie.poster).into(binding.seriePoster)

            // mostramos la puntuación
            binding.serieRating.apply {
                rating = serie.puntos * 5.0F / 10.0F
                setOnRatingBarChangeListener { ratingBar, fl, b -> pulsacionRating(ratingBar, fl, b, serie) }
            }

            // definimos el listener que responde a una pulsación corta
            binding.root.setOnClickListener { pulsacionSencilla(serie) }

            // definimos el listener que muestra el menú contextual
            binding.root.setOnLongClickListener {

                val pop = PopupMenu(binding.root.context, binding.serieTitulo)
                    pop.inflate(R.menu.item_menu)
                    pop.setOnMenuItemClickListener { pulsacionLarga(it, serie) }
                    pop.show()

                    true
            }
        }
    }
}
