package com.example.appnutri

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appnutri.Fragmentos_Cliente.Fragment_cliente_cuenta
import com.example.appnutri.Fragmentos_Cliente.Fragment_cliente_dashboard
import com.example.appnutri.databinding.ActivityMainClienteBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivityCliente : AppCompatActivity() {

    private lateinit var binding: ActivityMainClienteBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        verFragmentoDashboard()

        binding.BottomNvCliente.setOnItemSelectedListener {item->
            when(item.itemId){
                R.id.Menu_dashboard_cl ->{
                    verFragmentoDashboard()
                    true
                }
                R.id.Menu_cuenta_cl ->{
                    verFragmentoCuenta()
                    true
                }
                else ->{
                    false
                }
            }
        }
    }

    private fun comprobarSesion(){
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this, ElegirRol::class.java))
            finishAffinity()
        }else{
            Toast.makeText(applicationContext, "Bienvenid@ ${firebaseUser.email}", Toast.LENGTH_SHORT).show()
        }
    }





    private fun verFragmentoDashboard(){
        val nombre_titulo = "Dashboard"
        binding.TituloRLCliente.text = nombre_titulo
        val fragment = Fragment_cliente_dashboard()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentsCliente.id, fragment, "Fragment dashboard")
        fragmentTransaction.commit()
    }

    private fun verFragmentoCuenta(){
        val nombre_titulo = "Cuenta"
        binding.TituloRLCliente.text = nombre_titulo
        val fragment = Fragment_cliente_cuenta()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentsCliente.id, fragment, "Fragment cuenta")
        fragmentTransaction.commit()
    }
}