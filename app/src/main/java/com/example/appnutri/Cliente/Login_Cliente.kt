package com.example.appnutri.Cliente

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appnutri.databinding.ActivityLoginClienteBinding
import com.google.firebase.auth.FirebaseAuth

class Login_Cliente : AppCompatActivity() {

    private lateinit var binding : ActivityLoginClienteBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnLogin.setOnClickListener {
            validarInformacion()
        }

        binding.btnRegister.setOnClickListener {
            val result = Intent(this, Registro_Cliente::class.java)
            startActivity(result)
        }
    }


    private var email = ""
    private var password = ""
    private fun validarInformacion() {
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        if (email.isEmpty()){
            binding.email.error = "Ingrese su correo"
            binding.email.requestFocus()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.email.error = "Correo invalido"
            binding.email.requestFocus()
        }
        else if (password.isEmpty()){
            binding.password.error = "Ingrese su password"
            binding.password.requestFocus()
        }
        else{
            loginCliente()
        }
    }

    private fun loginCliente() {
        progressDialog.setMessage("Iniciando sesion")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this@Login_Cliente, Lunes::class.java))
                finishAffinity()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "No se pudo iniciar seson debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}