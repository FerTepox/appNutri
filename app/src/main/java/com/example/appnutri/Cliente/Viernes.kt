package com.example.appnutri.Cliente

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.appnutri.ElegirRol
import com.example.appnutri.databinding.ActivityViernesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Viernes : AppCompatActivity() {
    private lateinit var binding : ActivityViernesBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViernesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()

        cargarInformacion()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.DiaAnterior.setOnClickListener {
            val intent = Intent(this, Jueves::class.java)
            startActivity(intent)
        }
        binding.DiaSiguiente.setOnClickListener {
            val intent = Intent(this, Sabado::class.java)
            startActivity(intent)
        }
        binding.btnDatos.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, ElegirRol::class.java))
            finishAffinity()
        }
    }



    private var lunest1 = ""
    private var lunesd1 = ""
    private var lunest2 = ""
    private var lunesd2 = ""
    private var lunest3 = ""
    private var lunesd3 = ""
    private var lunest4 = ""
    private var lunesd4 = ""
    private var lunest5 = ""
    private var lunesd5 = ""


    private fun cargarInformacion() {
        val firestore = FirebaseFirestore.getInstance()
        val usuariosCollection = firestore.collection("usuarios")


        usuariosCollection.document(firebaseAuth.uid!!)
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    Log.e("cargarInformacion", "Error al obtener datos del usuario", error)
                    return@addSnapshotListener
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Obtener la información en tiempo real
                    val lunest1 = "${documentSnapshot.getString("viernest1")}"
                    val lunesd1 = "${documentSnapshot.getString("viernesd1")}"
                    val lunest2 = "${documentSnapshot.getString("viernest2")}"
                    val lunesd2 = "${documentSnapshot.getString("viernesd2")}"
                    val lunest3 = "${documentSnapshot.getString("viernest3")}"
                    val lunesd3 = "${documentSnapshot.getString("viernesd3")}"
                    val lunest4 = "${documentSnapshot.getString("viernest4")}"
                    val lunesd4 = "${documentSnapshot.getString("viernesd4")}"
                    val lunest5 = "${documentSnapshot.getString("viernest5")}"
                    val lunesd5 = "${documentSnapshot.getString("viernesd5")}"

                    // Setear
                    binding.EtALunest1.setText(lunest1)
                    binding.EtALunesd1.setText(lunesd1)
                    binding.EtALunest2.setText(lunest2)
                    binding.EtALunesd2.setText(lunesd2)
                    binding.EtALunest3.setText(lunest3)
                    binding.EtALunesd3.setText(lunesd3)
                    binding.EtALunest4.setText(lunest4)
                    binding.EtALunesd4.setText(lunesd4)
                    binding.EtALunest5.setText(lunest5)
                    binding.EtALunesd5.setText(lunesd5)
                }
            }
    }
}