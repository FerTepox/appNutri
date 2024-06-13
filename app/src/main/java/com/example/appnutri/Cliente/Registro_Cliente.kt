package com.example.appnutri.Cliente

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appnutri.databinding.ActivityRegistroClienteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Registro_Cliente : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroClienteBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnRegister.setOnClickListener {
            validarInformacion()
        }

    }

    var nombre = ""
    var email = ""
    var password = ""

    private fun validarInformacion() {
        nombre = binding.name.text.toString().trim()
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        if(nombre.isEmpty()){
            binding.name.error = "Ingrese nombre"
            binding.name.requestFocus()
        }
        else if (email.isEmpty()){
            binding.email.error = "Ingrese email"
            binding.email.requestFocus()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.email.error = "Email invalido"
            binding.email.requestFocus()
        }
        else if (password.isEmpty()){
            binding.password.error = "Ingrese la password"
            binding.password.requestFocus()
        }
        else{
            crearCuentaAdmin(email, password)
        }
    }

    private fun crearCuentaAdmin(email: String, password: String) {
        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                agregarInfoBD()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Ha fallado la creacion de la cuenta debido a ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun agregarInfoBD() {
        progressDialog.setMessage("Guardando informacion...")
        val tiempo = System.currentTimeMillis()
        val uid = firebaseAuth.uid

        val datos_cliente : HashMap<String, Any?> = HashMap()
        datos_cliente["uid"] = uid
        datos_cliente["nombres"] = nombre
        datos_cliente["email"] = email
        datos_cliente["rol"] = "cliente"
        datos_cliente["tiempo_registro"] = tiempo

        val firestore = FirebaseFirestore.getInstance()
        val usuariosCollection = firestore.collection("usuarios")

        uid?.let {
            usuariosCollection.document(it)
                .set(datos_cliente)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Cuenta creada", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Lunes::class.java))
                    finishAffinity()
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(
                        applicationContext,
                        "No se pudo guardar la informacion debido a ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } ?: run {
            progressDialog.dismiss()
            Toast.makeText(
                applicationContext,
                "No se pudo obtener el UID del usuario",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}