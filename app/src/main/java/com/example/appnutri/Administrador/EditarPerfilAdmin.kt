package com.example.appnutri.Administrador

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appnutri.databinding.ActivityEditarPerfilAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditarPerfilAdmin : AppCompatActivity() {

    private lateinit var binding : ActivityEditarPerfilAdminBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()

        cargarInformacion()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.BtnActualizarInfo.setOnClickListener {
            validarInformacion()
        }

    }

    private var nombres = ""
    private fun validarInformacion() {
        nombres = binding.EtANombres.text.toString().trim()
        if (nombres.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese un nuevo nombre", Toast.LENGTH_SHORT).show()
        }else{
            ActualizarInfo()
        }
    }

    private fun ActualizarInfo() {
        progressDialog.setMessage("Actualizando informacion")
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["nombres"] = nombres // Supongo que "nombres" es una variable miembro de la clase que contiene el nombre actualizado

        val firestore = FirebaseFirestore.getInstance()
        val usuariosCollection = firestore.collection("usuarios")

        usuariosCollection.document(firebaseAuth.uid!!)
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

        usuariosCollection.document(firebaseAuth.uid!!)
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    Log.e("cargarInformacion", "Error al obtener datos del usuario", error)
                    return@addSnapshotListener
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Obtener la información en tiempo real
                    val nombre = "${documentSnapshot.getString("nombres")}"

                    // Setear
                    binding.EtANombres.setText(nombre)
                }
            }
    }
}