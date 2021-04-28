package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val listaDeAmigos = mutableListOf<Usuario>()

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

  fun agregarAmigo(amigo: Usuario){
    if ( listaDeAmigos.count() != 0 && listaDeAmigos.contains(amigo))
      error("Imposible agregar amigo ya que el mismo lo es actualmente")
    else
      listaDeAmigos.add(amigo)
  }

  fun cantidadDeAmigos() = listaDeAmigos.count()

  fun puedeVerPublicacion(usuario: Usuario , publicacion: Publicacion) : Boolean
  {
    val encontrada = publicaciones.first { it.equals(publicacion) }

    return encontrada.tipoPermiso.puedeVerPublicacion(usuario)

  }

  fun determinarLosMejoresAmigos() : List<Usuario>
  {
    var usuariosMejoresAmigos = mutableListOf<Usuario>()
    var usuariosQueLikearonTodasLasPublicaciones = mutableListOf<Usuario>()

   for ( publicacion in publicaciones)
   {
     usuariosMejoresAmigos.addAll(publicacion.listaDeLikes)
   }

   for ( usuario in usuariosMejoresAmigos )
   {
     if ( publicaciones.all { it.listaDeLikes.contains( usuario) })
       usuariosQueLikearonTodasLasPublicaciones.add(usuario)
   }

    return usuariosQueLikearonTodasLasPublicaciones
  }

  fun amigoMasPopular():Usuario
  {
       return listaDeAmigos.maxByOrNull { it.cantidadTotalDeLikesEnTodasLasPublicaciones() }!!
  }

  fun cantidadTotalDeLikesEnTodasLasPublicaciones(): Int
  {
    return publicaciones.sumBy { it.cantidadDeLikes() }
  }

  fun usuarioStalkeaAmigo( usuario: Usuario): Boolean
  {
    var cantidadDePublicaciones = publicaciones.count()
    var cantidadDeLikesDelUser = publicaciones.count { it.listaDeLikes.contains(usuario) }

    return cantidadDeLikesDelUser >= (cantidadDePublicaciones * 0.9 ).toInt()

  }

  }


