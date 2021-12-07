package com.pmdm.flixnet

/**
 * Antonio José Sánchez
 * Programación Multimedia y de Dispositivos Móviles
 * curso 2021|22
 */

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.RatingBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pmdm.flixnet.adaptadores.SeriesAdaptador
import com.pmdm.flixnet.database.SeriesDatabase
import com.pmdm.flixnet.databinding.ActivityMainBinding
import com.pmdm.flixnet.entidades.Serie
import com.pmdm.flixnet.entidades.Usuario
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var bd: SeriesDatabase

    private lateinit var adaptador: SeriesAdaptador
    private lateinit var datos: MutableList<Serie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // vinculación de vistas
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // instanciar el servicio de autenticación
        auth = Firebase.auth

        // instanciamos el servicio de bases de datos
        bd = SeriesDatabase.getSeriesDatabase(this)!!

        // recuperamos información almacenada con PUTEXTRA en la intención
        val usuario: Usuario = intent.extras?.getSerializable("_usuario") as Usuario

        // recuperamos información de la base de datos
        datos = bd.getSeriesDao().getShows()

        // definimos los métodos escuchadores
        // pulsación sencilla sobre un determinado ítem: abrimos una actividad para visualizar
        // información sobre la serie elegida.
        val gestionarPulsacionSencilla: (Serie) -> Unit =
        {
            val intent = Intent(this, ViewActivity::class.java)
            intent.putExtra("_id", it.idSer)
            startActivity(intent)
        }

        // pulsación prolongada sobre un determinado ítem
        val gestionarPulsacionLarga: (MenuItem, Serie) -> Boolean =
        {
            item: MenuItem, serie: Serie ->

            when(item.itemId) {

                R.id.mnuEditar -> {
                    Snackbar.make(binding.root, "la opción EDITAR queda propuesta como ejercicio para el alumno", Snackbar.LENGTH_LONG)
                            .show()
                }

                R.id.mnuBorrar -> {

                    // eliminamos la serie de la base de datos
                    bd.getSeriesDao().deleteShow(serie)

                    // buscamos el índice de la serie
                    val idx = datos.indexOfFirst { it.equals(serie) }

                    // eliminamos la serie de la colección de datos
                    datos.removeAt(idx)

                    // notificamos el cambio al adaptador
                    adaptador.notifyItemRemoved(idx)
                }
            }

            false
        }

        // pulsación sobre la barra de puntuación: actualizamos el registro en la base de datos
        val gestionarPulsacionRating: (RatingBar, Float, Boolean, Serie) -> Unit =
        {
            rating: RatingBar, f: Float, b: Boolean, serie: Serie ->

            serie.puntos = f*10F/5F
            bd.getSeriesDao().updateShow(serie)
        }

        // creamos el adaptador
        adaptador = SeriesAdaptador(datos).apply {
            pulsacionSencilla = gestionarPulsacionSencilla
            pulsacionLarga    = gestionarPulsacionLarga
            pulsacionRating   = gestionarPulsacionRating
        }

        // asociamos el adaptador y configuramos el recyclerView
        binding.rv.apply {
            adapter = adaptador
            setHasFixedSize(true)
        }
    }

    /**
     * Bloqueamos la vuelta a la actividad anterior
     */
    override fun onBackPressed() { }

    /**
     * Creamos el MENÚ DE APLICACIÓN
     * @param menu: Menu?
     * @return Boolean
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Gestionamos la pulsación sobre las opciones del menú
     * @param item: MenuItem
     * @return Boolean
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when(item.itemId)
        {
            // no se gestiona ya que estamos en la actividad correspondiente
            R.id.mnuSeries -> { true }

            R.id.mnuFavoritos -> {
                Snackbar.make(binding.root, "la opción FAVORITOS queda propuesta como ejercicio para el alumno", Snackbar.LENGTH_LONG)
                    .show()
                true
            }

            R.id.mnuPerfil -> {
                Snackbar.make(binding.root, "la opción PERFIL queda propuesta como ejercicio para el alumno", Snackbar.LENGTH_LONG)
                        .show()
                true
            }

            R.id.mnuSalir -> {
                auth.signOut()
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}










