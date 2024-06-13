package com.example.appnutri.Fragmentos_Admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appnutri.Administrador.EditarPerfilAdmin
import com.example.appnutri.ElegirRol
import com.example.appnutri.databinding.FragmentAdminCuentaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Fragment_admin_cuenta : Fragment() {

    private lateinit var binding: FragmentAdminCuentaBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminCuentaBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        cargarInformacion()

        binding.EditarPerfilAdmin.setOnClickListener {
            startActivity(Intent(mContext, EditarPerfilAdmin::class.java))
        }

        binding.CerrarSesionAdmin.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(context, ElegirRol::class.java))
            activity?.finishAffinity()
        }
    }

    private fun cargarInformacion() {
        val firestore = FirebaseFirestore.getInstance()
        val usuariosCollection = firestore.collection("usuarios")

        usuariosCollection.document("${firebaseAuth.uid}")
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    Log.e("cargarInformacion", "Error al obtener datos del usuario", error)
                    return@addSnapshotListener
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Obtener los datos del usuario actual
                    val nombres = "${documentSnapshot.getString("nombres")}"
                    val email = "${documentSnapshot.getString("email")}"
                    val rol = "${documentSnapshot.getString("rol")}"

                    // Setear la información
                    binding.TxtNombresAdmin.text = nombres
                    binding.TxtEmailAdmin.text = email
                    binding.TxtRolAdmin.text = rol
                }
            }
    }

}