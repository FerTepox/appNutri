package com.example.appnutri.Administrador

class ModeloUsuario {
    var email : String = ""
    var nombres : String = ""
    var rol : String = ""
    var tiempo_registro : Long = 0
    var uid : String = ""

    constructor()

    constructor(email: String, nombres: String, rol: String, tiempo_registro: Long, uid: String) {
        this.email = email
        this.nombres = nombres
        this.rol = rol
        this.tiempo_registro = tiempo_registro
        this.uid = uid
    }


}