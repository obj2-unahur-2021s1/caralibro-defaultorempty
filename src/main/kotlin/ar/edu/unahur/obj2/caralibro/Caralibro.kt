package ar.edu.unahur.obj2.caralibro

class Caralibro {
    val listaDeUsuarios = mutableListOf<Usuario>()

    fun agregarUsuario(usuario :Usuario)
    {
        if (listaDeUsuarios.count() != 0 && listaDeUsuarios.contains(usuario) )
            error("El usuario ya existe ")
        else
            listaDeUsuarios.add(usuario)
    }

    fun saberCuantoEspacioOcupaElTotalDeLasPublicacionesDelUsuario(usuario : Usuario) : Int
    {
        return usuario.espacioDePublicaciones()
    }

    fun cuantosLikesTieneUnaPublicacion( publicacion: Publicacion ) = publicacion.cantidadDeLikes()

    fun saberSiUnUsuarioEsMasAmistosoQueOtro(usuario: Usuario, usuarioComparar: Usuario) : Boolean
    {
        return usuario.cantidadDeAmigos() > usuarioComparar.cantidadDeAmigos()
    }

}