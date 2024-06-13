package com.example.appnutri.Administrador

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appnutri.databinding.ItemUsuarioAdminBinding


class AdaptadorUsuario : RecyclerView.Adapter<AdaptadorUsuario.HolderUsuario>, Filterable{

    private lateinit var binding: ItemUsuarioAdminBinding

    private val m_context : Context
    public var usuarioArrayList : ArrayList<ModeloUsuario>


    private var filtroLista : ArrayList<ModeloUsuario>
    private var filtro : FiltroUsuario? = null

    constructor(m_context: Context, usuarioArrayList: ArrayList<ModeloUsuario>) {
        this.m_context = m_context
        this.usuarioArrayList = usuarioArrayList
        this.filtroLista = usuarioArrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderUsuario {
        binding = ItemUsuarioAdminBinding.inflate(LayoutInflater.from(m_context), parent, false)
        return HolderUsuario(binding.root)
    }

    override fun getItemCount(): Int {
        return usuarioArrayList.size
    }

    override fun onBindViewHolder(holder: HolderUsuario, position: Int) {
        val modelo = usuarioArrayList[position]
        val email = modelo.email
        val nombres = modelo.nombres
        val rol = modelo.rol
        val tiempo_registro = modelo.tiempo_registro
        val uid = modelo.uid

        holder.UsuarioTv.text = nombres

        holder.itemView.setOnClickListener {
            val intent = Intent(m_context, ModificarPlanAdmin::class.java)
            intent.putExtra("idUsuario", uid)
            m_context.startActivity(intent)
        }
    }


    inner class HolderUsuario (itemView : View) : RecyclerView.ViewHolder(itemView){
        var UsuarioTv : TextView = binding.ItemNombreUsuarioA
        var eliminarUsuarioIb : ImageButton = binding.EliminarUsuario

    }

    override fun getFilter(): Filter {
        if (filtro == null){
            filtro = FiltroUsuario(filtroLista, this)

        }
        return filtro as FiltroUsuario
    }


}