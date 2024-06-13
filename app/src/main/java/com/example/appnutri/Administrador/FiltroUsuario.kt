package com.example.appnutri.Administrador

import android.widget.Filter

class FiltroUsuario : Filter {

    private var filtroLista : ArrayList<ModeloUsuario>
    private var adaptadorUsuario : AdaptadorUsuario

    constructor(filtroLista: ArrayList<ModeloUsuario>, adaptadorUsuario: AdaptadorUsuario) {
        this.filtroLista = filtroLista
        this.adaptadorUsuario = adaptadorUsuario
    }

    override fun performFiltering(usuario: CharSequence?): FilterResults {
        var usuario = usuario
        var resultados = FilterResults()

        if (usuario != null && usuario.isEmpty()){
            usuario = usuario.toString().uppercase()
            val modeloFiltrado : ArrayList<ModeloUsuario> = ArrayList()
            for (i in 0 until filtroLista.size){
                if (filtroLista[i].nombres.uppercase().contains(usuario) ){
                    modeloFiltrado.add(filtroLista[i])
                }
                resultados.count = modeloFiltrado.size
                resultados.values = modeloFiltrado
            }
        }
        else{
            resultados.count = filtroLista.size
            resultados.values = filtroLista
        }
        return resultados
    }

    override fun publishResults(p0: CharSequence?, resultados: FilterResults) {
        adaptadorUsuario.usuarioArrayList = resultados.values as ArrayList<ModeloUsuario>
        adaptadorUsuario.notifyDataSetChanged()
    }
}