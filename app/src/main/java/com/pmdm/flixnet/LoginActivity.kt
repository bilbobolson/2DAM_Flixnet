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
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pmdm.flixnet.database.SeriesDatabase
import com.pmdm.flixnet.databinding.ActivityLoginBinding
import com.pmdm.flixnet.entidades.Serie
import com.pmdm.flixnet.entidades.Usuario


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    /**
     * Registramos cómo reacciona la actividad de LOGIN a la respuesta de la actividad REGISTRO.
     * Debemos registrar SIEMPRE el resultado de la actividad  antes de que la actividad se cree.
     */
    private val respuestaRegistro = registerForActivityResult(StartActivityForResult()) {
        /**
         * La lambda contiene la lógica que se ejecutará cuando la actividad REGISTRO responda.
         */
        if (it.resultCode == RESULT_OK)
            it.data?.extras?.getString("_response")?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }

        else
            Snackbar.make(binding.root, R.string.err_registro, Snackbar.LENGTH_LONG)
                    .show()
    }

    /** */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Vinculación de vistas
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // instanciamos el servicio de autenticación de Firebase
        auth = Firebase.auth

        // inicializamos la base de datos si es necesario
        inicializarBaseDatos()

        // comprobamos si hay usuario logueado
        if (auth.currentUser!=null) goMain(auth.uid.toString())

        //
        binding.btnLogin.setOnClickListener    { login(it) }
        binding.btnRegistro.setOnClickListener { registro(it) }
    }

    /**
     * Realizamos el proceso de login sobre Firebase
     * @param view: View
     */
    fun login(view: View)
    {
        val email: String = binding.email.text.toString().trim()
        val pass : String = binding.password.text.toString().trim()

        //
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) {

                if (it.isSuccessful) goMain(auth.uid.toString())
                else
                    // Se ha producido un error y mostramos un mensaje notificándolo
                    Snackbar.make(view,R.string.err_login, Snackbar.LENGTH_LONG)
                            .show()
            }
    }


    /**
     * Accedemos a la actividad de REGISTRO
     * @param view: View
     */
    fun registro(view: View) {

        // creamos la intención y lanzamos la intención
        val intencion = Intent(this, RegisterActivity::class.java)
        respuestaRegistro.launch(intencion)
    }

    /**
     * Accede a la actividad principal
     * @param uid: String
     */
    private fun goMain(uid: String)
    {
        // buscamos al usuario en la base de datos a partir del UID
        val db = SeriesDatabase.getSeriesDatabase(this)
        //db?.getSeriesDao()?.
        val usuario = db?.getSeriesDao()?.getUserById(uid)

        // expresamos la intención de iniciar la actividad MainActivity que
        // está definida por la clase MainActivity::class.java
        val intencion = Intent(this, MainActivity::class.java)

        // almacenamos información dentro de la intención a través de PUTEXTRA
        intencion.putExtra("_usuario", usuario)

        // iniciamos la intención
        startActivity(intencion)
    }

    /**
     * Inicializamos la base de datos comprobando si hay registros en las tablas. En caso de
     * que no los haya, insertamos información sobre usuarios y series.
     */
    private fun inicializarBaseDatos()
    {
        val dao = SeriesDatabase.getSeriesDatabase(this)?.getSeriesDao()

        if (dao?.countUsers() == 0)
        {
            dao.insertUser(Usuario("lw2N59hdxTZrVdM1ndTAHBGmp6x2", "Bruce", "Wayne"))
        }

        if(dao?.countSeries() == 0)
        {
            dao.insertShow(
                Serie(0, "Ojo de halcón", 8.5F ,"https://www.themoviedb.org/t/p/w600_and_h900_bestv2/ommKNzt6wmtz3mQS3e3rrDnISOM.jpg"),
                Serie(0, "Chucky", 8.0F , "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/sSSafjHp0RmP923L5udSf7R5RqM.jpg"),
                Serie(0, "La rueda del tiempo",8.4F , "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/7cVmgz7TzkZf64VD6Vc3AOZS1uA.jpg"),
                Serie(0,"The Flash", 7.4F ,  "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/2O8tlUzKS3uddGW5D2B9XM6b4e.jpg"),
                Serie(0, "Riverdale",8.0F, "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/mlk5jA1ewWUxle6LY9bEi7aiI97.jpg"),
                Serie(0, "Lucifer", 8.5F, "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/wQh2ytX0f8IfC3b2mKpDGOpGTXS.jpg"),
                Serie(0, "Los Simpsons", 8.9F, "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/u8BMLmwoc7YPHKSWawOOqC1c8lJ.jpg"),
                Serie(0, "Nancy Drew", 9.5F, "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/zSa24rXwk1Uwnw91kvdOTWuyOTI.jpg"),
                Serie(0,"Sólo asesinatos en el edificio", 9.4F,"https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fOEiX00L6uw4GSYKD0JrxRr7Zrc.jpg"),
                Serie(0, "Ted Lasso", 10.0F, "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/39rtdG2QoPIOISmS6KNdJDes8vy.jpg"),
                Serie(0, "La maldición de Hill House", 10.0F, "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/flBIpQHga5217QgLE4BanIAzMix.jpg"),
                Serie(0, "Perdidos en el espacio", 7.8F, "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/qv2PvsVjLttp4FmUUJ1G9d11qTe.jpg"),
                Serie(0, "Brooklyn 99", 8.8F,"https://www.themoviedb.org/t/p/w600_and_h900_bestv2/wd5JwnNOqEDIKZ672wjEo8hzL7k.jpg"),
                Serie(0,"Lo que hacemos en las sombras", 7.0F,"https://www.themoviedb.org/t/p/w600_and_h900_bestv2/eCsh3Y9FYBMMHcy1pjrQgVwLCaM.jpg"),
                Serie(0, "Downton Abbey", 10.0F, "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/3mYwiv6rsnEHnbark7a24Jbx2M9.jpg"),
                Serie(0, "Cowboy Bebop", 9.3F, "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lSsCxZ1dnjcB4cmXSgMJt5qunOu.jpg"),
                Serie(0, "Evil", 8.0F, "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/mZvV71dsQkSs5cePbojeCsVAZlo.jpg"),
                Serie(0, "Masters del Universo: Revelación", 8.2F, "https://m.media-amazon.com/images/M/MV5BMjRkNmE0NTYtN2IzNi00MzFiLTgzMDMtYjU0OTUyMzM4Njc2XkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg"),
                Serie(0,"Inspector George Gently", 9.5F, "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/vNjE3e5zSLhz0fQdovTlGdSo6Gb.jpg")
            )
        }
    }


}







