package com.example.appnutri

import android.R.attr
import android.R.attr.name
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class Register : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //setup
        setup()
    }
    private fun setup() {
        title = "Autenticacion"
        val btn_register = findViewById<Button>(R.id.btn_register)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

        btn_register.setOnClickListener{
            if (email.text.isNotEmpty() && password.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email.text.toString()
                        ,password.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful){
                            showHome(it.result?.user?.email?:"", ProviderType.Basic)//se colocan ? por si es vacio pero
                            // ya que esta dentro de la condicion sabemos que no es vacio nunca
                        }else{
                            showAlert ()//mostrar error
                        }
                    }
            }
        }
    }

    private fun showAlert () {
        val builder = AlertDialog.Builder( this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()}

    private fun showAlert2 () {
        val builder = AlertDialog.Builder( this)
        builder.setTitle("Error")
        builder.setMessage("Usuario o contraseña son incorrectos")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()}

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this,Home::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }

}