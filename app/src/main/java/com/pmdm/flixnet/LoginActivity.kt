package com.pmdm.flixnet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pmdm.flixnet.databinding.ActivityLoginBinding
import com.pmdm.flixnet.objetos.Usuario


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    /**
     * Registramos cómo reacciona la actividad de LOGIN a la respuesta
     * de la actividad de REGISTRO. Este registro debe realizarse SIEMPRE
     * antes de que la actividad de LOGIN se cree.
     */
    private val respuestaRegistro = registerForActivityResult(StartActivityForResult()) {
        /**
         * La lambda contiene la lógica que se ejecutará cuando la actividad
         * REGISTRO responda.
         */
        if (it.resultCode == RESULT_OK)
            it.data?.extras?.getString("_response")?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }

        else
            Snackbar.make(binding.root, R.string.err_registro, Snackbar.LENGTH_LONG)
                    .show()
    }

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_login)
        // ################

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // instanciamos el servicio de autenticación de Firebase
        auth = Firebase.auth

        // botón login
        binding.btnLogin.setOnClickListener { login(it) }
        binding.btnRegistro.setOnClickListener { registro(it) }

    }

    /**
     */
    fun login(view: View) {

        val email: String = binding.email.text.toString().trim()
        val pass : String = binding.password.text.toString().trim()

        //resources.getString(R.string.err_email_pass)

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) {

                if (it.isSuccessful) {

                    // Emulamos una búsqueda del usuario en la base de datos: TEMPORAL
                    val usuario = Usuario("Laura Lérida", "email@email.com", 20)

                    // expresamos la intención de iniciar la actividad MainActivity que
                    // está definida por la clase MainActivity::class.java
                    val intencion = Intent(this, MainActivity::class.java)

                    // almacenamos información dentro de la intención a través de PUTEXTRA
                    //intencion.putExtra("nombre", "Laura Lérida")
                    //intencion.putExtra("edad", 20)
                    intencion.putExtra("_usuario", usuario)

                    // almacenamos información dentro de la intención a través de BUNDLE
                    //val bundle = Bundle()
                    //bundle.putString("nombre", "Laura Lérida")
                    //bundle.putInt("edad", 20)
                    //bundle.putSerializable("_usuario", usuario)

                    // asocio el diccionario a la intención
                    //intencion.putExtra("_usuario", bundle)

                    // iniciamos la intención
                    startActivity(intencion)

                } else {
                    // Se ha producido un error y mostramos un mensaje notificándolo
                    Snackbar.make(view,R.string.err_login, Snackbar.LENGTH_LONG)
                            .show()
                }

            }

    }

    /**
     */
    fun registro(view: View) {

        // creamos la intención
        val intencion = Intent(this, RegisterActivity::class.java)

        // iniciar la actividad a la espera de un resultado
        respuestaRegistro.launch(intencion)
    }





}







