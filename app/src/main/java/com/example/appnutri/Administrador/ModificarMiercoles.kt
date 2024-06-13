package com.example.appnutri.Administrador

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appnutri.Home
import com.example.appnutri.databinding.ActivityModificarMiercolesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ModificarMiercoles : AppCompatActivity() {
    private lateinit var binding : ActivityModificarMiercolesBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private var idUsuario = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarMiercolesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        idUsuario = intent.getStringExtra("idUsuario")!!

        firebaseAuth= FirebaseAuth.getInstance()

        cargarInformacion()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnActualizar.setOnClickListener {
            validarInformacion()
        }

        binding.DiaAnterior.setOnClickListener {
            val intent = Intent(this, ModificarMartes::class.java)
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)
        }
        binding.DiaSiguiente.setOnClickListener {
            val intent = Intent(this, ModificarJueves::class.java)
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)
        }
        binding.btnDatos.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
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
    private fun validarInformacion() {
        lunest1 = binding.EtALunest1.text.toString().trim()
        lunesd1 = binding.EtALunesd1.text.toString().trim()
        lunest2 = binding.EtALunest2.text.toString().trim()
        lunesd2 = binding.EtALunesd2.text.toString().trim()
        lunest3 = binding.EtALunest3.text.toString().trim()
        lunesd3 = binding.EtALunesd3.text.toString().trim()
        lunest4 = binding.EtALunest4.text.toString().trim()
        lunesd4 = binding.EtALunesd4.text.toString().trim()
        lunest5 = binding.EtALunest5.text.toString().trim()
        lunesd5 = binding.EtALunesd5.text.toString().trim()
        if (lunest1.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese un nuevo nombre", Toast.LENGTH_SHORT).show()
        }else{
            ActualizarInfo()
        }
    }

    private fun ActualizarInfo() {
        progressDialog.setMessage("Actualizando informacion")

        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["miercolest1"] = lunest1
        hashMap["miercolesd1"] = lunesd1
        hashMap["miercolest2"] = lunest2
        hashMap["miercolesd2"] = lunesd2
        hashMap["miercolest3"] = lunest3
        hashMap["miercolesd3"] = lunesd3
        hashMap["miercolest4"] = lunest4
        hashMap["miercolesd4"] = lunesd4
        hashMap["miercolest5"] = lunest5
        hashMap["miercolesd5"] = lunesd5

        val firestore = FirebaseFirestore.getInstance()
        val usuariosCollection = firestore.collection("usuarios")

        usuariosCollection.document(idUsuario)
            .update(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Se actualizó su información", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "No se realizó la actualización debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun cargarInformacion() {
        val firestore = FirebaseFirestore.getInstance()
        val usuariosCollection = firestore.collection("usuarios")


        usuariosCollection.document(idUsuario)
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    Log.e("cargarInformacion", "Error al obtener datos del usuario", error)
                    return@addSnapshotListener
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Obtener la información en tiempo real
                    val lunest1 = "${documentSnapshot.getString("miercolest1")}"
                    val lunesd1 = "${documentSnapshot.getString("miercolesd1")}"
                    val lunest2 = "${documentSnapshot.getString("miercolest2")}"
                    val lunesd2 = "${documentSnapshot.getString("miercolesd2")}"
                    val lunest3 = "${documentSnapshot.getString("miercolest3")}"
                    val lunesd3 = "${documentSnapshot.getString("miercolesd3")}"
                    val lunest4 = "${documentSnapshot.getString("miercolest4")}"
                    val lunesd4 = "${documentSnapshot.getString("miercolesd4")}"
                    val lunest5 = "${documentSnapshot.getString("miercolest5")}"
                    val lunesd5 = "${documentSnapshot.getString("miercolesd5")}"

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