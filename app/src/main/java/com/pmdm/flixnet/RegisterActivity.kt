package com.pmdm.flixnet

/**
 * Antonio José Sánchez
 * Programación Multimedia y de Dispositivos Móviles
 * curso 2021|22
 */

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pmdm.flixnet.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Vinculación de vistas
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // instanciamos el servicio de autenticación de Firebase
        auth = Firebase.auth

        //
        binding.btnRegistrar.setOnClickListener { registrar(it) }
    }

    /**
     * Al regresar a la actividad anterior sin realizar un registro indicamos tal situación
     */
    override fun onBackPressed()
    {
        setResult(RESULT_CANCELED)
        super.onBackPressed()
    }

    /**
     * Realizamos el profceso de registro
     * @param view: View
     */
    private fun registrar(view: View) {
        // recuperamos la información de los campos
        val email: String = binding.email.text.toString().trim()
        val pass: String = binding.password.text.toString().trim()

        if ((email != "") && (pass != "")) {
            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this) {

                    var msg = ""

                    if (it.isSuccessful) msg = resources.getString(R.string.msg_registro)
                    else                 msg = resources.getString(R.string.err_registro)

                    // regresamos a la actividad anterior
                    val intencion = Intent()
                    intencion.putExtra("_response", msg)
                    setResult(RESULT_OK, intencion)
                    finish()
                }
        } else {
            Snackbar.make(binding.root, R.string.err_email_pass, Snackbar.LENGTH_LONG).show()
        }
    }

}












