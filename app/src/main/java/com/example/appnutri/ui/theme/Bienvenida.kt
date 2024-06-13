package com.example.appnutri.ui.theme

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.appnutri.ElegirRol
import com.example.appnutri.Home
import com.example.appnutri.MainActivityCliente
import com.example.appnutri.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Bienvenida : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida2)
        firebaseAuth = FirebaseAuth.getInstance()
        VerBienvenida()
    }

    fun VerBienvenida(){
        object : CountDownTimer(2000,1000){
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
            val usuariosCollection = firestore.collection("Usuarios")

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
                                startActivity(Intent(this@Bienvenida, MainActivityCliente::class.java))
                                finishAffinity()
                            }
                            else -> {
                                // Manejar otros roles si es necesario
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    // Manejar el fallo
                }
        }
    }
}