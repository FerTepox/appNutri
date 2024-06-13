package com.example.appnutri

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appnutri.Cliente.Lunes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Bienvenida : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        firebaseAuth = FirebaseAuth.getInstance()
        VerBienvenida()
    }

    private fun VerBienvenida() {
        object : CountDownTimer(2000, 1000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                ComprobarSesion()
            }
        }.start()
    }
    fun ComprobarSesion(){
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser == null) {
            startActivity(Intent(this, ElegirRol::class.java))
            finishAffinity()
        } else {
            val firestore = FirebaseFirestore.getInstance()
            val usuariosCollection = firestore.collection("usuarios")

            usuariosCollection.document(firebaseUser.uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val rol = documentSnapshot.getString("rol")

                        when (rol) {
                            "admin" -> {
                                startActivity(Intent(this@Bienvenida, Home::class.java))
                                finishAffinity()
                            }
                            "cliente" -> {
                                startActivity(Intent(this@Bienvenida, Lunes::class.java))
                                finishAffinity()
                            }
                            else -> {
                                // Manejar otros roles si es necesario
                                Toast.makeText(
                                    applicationContext,
                                    "Rol desconocido: $rol",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        // El documento no existe, manejar según sea necesario
                        Toast.makeText(
                            applicationContext,
                            "El documento del usuario no existe",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnFailureListener { e ->
                    // Manejar el fallo
                    Toast.makeText(
                        applicationContext,
                        "Error al obtener datos del usuario: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}