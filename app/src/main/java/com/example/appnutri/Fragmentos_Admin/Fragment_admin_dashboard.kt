package com.example.appnutri.Fragmentos_Admin

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appnutri.Administrador.AdaptadorUsuario
import com.example.appnutri.Administrador.ModeloUsuario
import com.example.appnutri.databinding.FragmentAdminDashboardBinding
import com.google.firebase.firestore.FirebaseFirestore


class Fragment_admin_dashboard : Fragment() {

    private lateinit var binding : FragmentAdminDashboardBinding
    private lateinit var mContext: Context
    private lateinit var usuarioArrayList: ArrayList<ModeloUsuario>
    private lateinit var adaptadorUsuario: AdaptadorUsuario


    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ListarUsuarios()

        binding.BuscarUsuario.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(usuario: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    adaptadorUsuario.filter.filter(usuario)
                }catch (e: Exception){

                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


    }

    private fun ListarUsuarios(){
        usuarioArrayList = ArrayList()
        val firestore = FirebaseFirestore.getInstance()
        val usuariosCollection = firestore.collection("usuarios")

        usuariosCollection.get()
            .addOnSuccessListener { snapshot ->
                usuarioArrayList.clear()
                for (document in snapshot.documents) {
                    val modelo = document.toObject(ModeloUsuario::class.java)
                    modelo?.let { usuarioArrayList.add(it) }
                }

                adaptadorUsuario = AdaptadorUsuario(mContext, usuarioArrayList)
                binding.UsuariosRv.adapter = adaptadorUsuario
            }
            .addOnFailureListener { exception ->
                // Handle the exception
            }
    }


}