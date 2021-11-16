package com.pmdm.flixnet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pmdm.flixnet.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRegistrar.setOnClickListener { registrar(it) }
    }

    override fun onBackPressed()
    {
        setResult(RESULT_CANCELED)
        super.onBackPressed()
    }

    private fun registrar(view: View)
    {
        val email: String = binding.email.text.toString().trim()
        val pass : String = binding.password.text.toString().trim()

        if ((email!="") && (pass!=""))
            auth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this) {

                    var msg = ""

                    if (it.isSuccessful) msg = resources.getString(R.string.msg_registro)
                    else msg = resources.getString(R.string.err_registro)

                    val intencion = Intent()
                    intencion.putExtra("_response", msg)
                    setResult(RESULT_OK, intencion)
                    finish()
                }
    }
}












