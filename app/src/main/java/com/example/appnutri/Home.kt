package com.example.appnutri

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appnutri.Fragmentos_Admin.Fragment_admin_cuenta
import com.example.appnutri.Fragmentos_Admin.Fragment_admin_dashboard
import com.example.appnutri.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


enum class ProviderType{
    Basic
}

class Home : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance() //variable para llamar la base de datos



    private lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        ComprobarSesion()
        VerFragmentoDashboard()

        binding.BottomNvAdmin.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.Menu_panel->{
                    VerFragmentoDashboard()
                    true
                }
                R.id.Menu_cuenta->{
                    VerFragmentoCuenta()
                    true
                }
                else->{
                    false
                }
            }

        }

    }

    private fun VerFragmentoDashboard(){
        val nombre_titulo = "Dashboard"
        binding.TituloRLAdmin.text = nombre_titulo

        val fragment = Fragment_admin_dashboard()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentsAdmin.id, fragment, "Fragment dashboard")
        fragmentTransaction.commit()
    }
    private fun VerFragmentoCuenta(){
        val nombre_titulo = "Mi cuenta"
        binding.TituloRLAdmin.text = nombre_titulo

        val fragment = Fragment_admin_cuenta()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentsAdmin.id, fragment, "Fragment mi cuenta")
        fragmentTransaction.commit()
    }

    private fun ComprobarSesion(){
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this, ElegirRol::class.java))
            finishAffinity()
        }else{
            /*Toast.makeText(applicationContext, "Bienvenido(a) ${firebaseUser.email}", Toast.LENGTH_SHORT).show()*/
        }
    }

}