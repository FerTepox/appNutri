package com.example.appnutri

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appnutri.Administrador.Login_Admin
import com.example.appnutri.Cliente.Login_Cliente
import com.example.appnutri.databinding.ActivityElegirRolBinding

class ElegirRol : AppCompatActivity() {

    private lateinit var binding: ActivityElegirRolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElegirRolBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRolDoc.setOnClickListener {
            Toast.makeText(applicationContext, "Rol Doctor", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@ElegirRol, Login_Admin::class.java))
        }
        binding.btnRolCliente.setOnClickListener {
            Toast.makeText(applicationContext, "Rol Cliente", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@ElegirRol, Login_Cliente::class.java))
        }
    }
}