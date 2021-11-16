package com.pmdm.flixnet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pmdm.flixnet.databinding.ActivityMainBinding
import com.pmdm.flixnet.objetos.Usuario

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Asociamos el menú contextual a una vista
        registerForContextMenu(binding.etiqueta)

        // instanciar el servicio de autenticación
        auth = Firebase.auth

        // recuperamos información almacenada con PUTEXTRA en la intención
        //val nombre = intent.extras?.get("nombre")
        val usuario: Usuario = intent.extras?.getSerializable("_usuario") as Usuario

        // recuperamos información almacenada en un BUNDLE en la intención
        //val bundle = intent.extras?.getBundle("_usuario")
        //val nombre = bundle?.getString("nombre")

        with(binding.etiqueta) {
            text = resources.getString(R.string.msg_bienvenida, usuario.nombre)
            //binding.etiqueta.text = binding.etiqueta.text.toString() + nombre
        }

        /**
         * Lazamos una intención implícita (EJEMPLO)
         */
        binding.implicita.setOnClickListener {

            // definimos la intención implícita
            val intencion =  Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain" // tipo de información (MIME)
                putExtra(Intent.EXTRA_TEXT, "Te recomiendo que veas LOCKE & KEY")
            }

            // lanzamos la intención pero previamente tendremos que comprobar
            // si hay algún paquete (app) instalado en el dispositivo capaz
            // de atender a la intención.
            if (intencion.resolveActivity(packageManager) != null)
                startActivity(intencion)
        }

    }

    /**
     * Bloqueamos la vuelta a la actividad anterior
     */
    override fun onBackPressed() { }

    /**
     * Creamos el MENÚ DE APLICACIÓN
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {

            R.id.mnuPerfil -> {
                Log.i("XXXX", "Has pulsado en la opción PERFIL")
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

    /**
     * creamos el MENÚ CONTEXTUAL FLOTANTE
     */
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?,
                                     menuInfo: ContextMenu.ContextMenuInfo?)
    {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.item_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.mnuEditar  -> {
                Log.i("XXXX", "Has pulsado en la opción EDITAR")
                true
            }

            R.id.mnuBorrar -> {
                Log.i("XXXX", "Has pulsado en la opción BORRAR")
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

}










