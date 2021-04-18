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
    //return publicaciones.contains(publicacion)
    val encontrada = publicaciones.first { it.equals(publicacion) }

    return encontrada.tipoPermiso.puedeVerPublicacion(usuario)

  }

  fun darleLikePublicacion(usuario: Usuario , publicacion: Publicacion) :Unit
  {
    publicaciones.contains(publicacion)
  }

  fun determinarLosMejoresAmigos() : List<Usuario>
  {
    var usuariosMejoresAmigos = mutableListOf<Usuario>()

    var a = publicaciones.distinctBy { it.listaDeLikes.distinct() }

    return usuariosMejoresAmigos
  }

  fun amigoMasPopular() :Usuario
  {
   // var a = Usuario()

    //a = listaDeAmigos.maxByOrNull { it.cantidadTotalDeLikesEnTodasLasPublicaciones() }

    return listaDeAmigos.maxByOrNull { it.cantidadTotalDeLikesEnTodasLasPublicaciones() }!!
  }

  fun cantidadTotalDeLikesEnTodasLasPublicaciones(): Int
  {
    return publicaciones.sumBy { it.cantidadDeLikes() }
  }

  }


