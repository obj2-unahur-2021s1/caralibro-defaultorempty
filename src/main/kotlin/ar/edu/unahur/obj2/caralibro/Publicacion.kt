package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion(var tipoPermiso : Permiso) {

  val listaDeLikes = mutableListOf<Usuario>()

  abstract fun espacioQueOcupa(): Int
  abstract fun cambiarTipoDePermiso(permiso: Permiso): Unit

  open fun agregarLike(usuario: Usuario) {
    if (listaDeLikes.count()!= 0 && listaDeLikes.contains(usuario)) {
      error("Publicacion ya likeada")
    } else {
      listaDeLikes.add(usuario)
    }
  }

  open fun cantidadDeLikes() : Int {
    return listaDeLikes.size
  }
}
  class Foto(val alto: Int, val ancho: Int, tipoPermiso: Permiso) : Publicacion(tipoPermiso) {
    var factorDeCompresion = 0.7
    override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt()

    fun cambiarFactorDeCompresion(nuevoFactorDeCompresion: Double) {
      factorDeCompresion = nuevoFactorDeCompresion
    }

    override fun cambiarTipoDePermiso(permiso: Permiso) {
      tipoPermiso = permiso
    }

  }

  class Texto(val contenido: String, tipoPermiso: Permiso) : Publicacion(tipoPermiso) {
    override fun espacioQueOcupa() = contenido.length

    override fun cambiarTipoDePermiso(permiso: Permiso) {
      tipoPermiso = permiso
    }
  }

  class Video(val duracionDelVideo: Int, val calidad: Calidad, tipoPermiso: Permiso) : Publicacion(tipoPermiso) {

    override fun espacioQueOcupa(): Int {
      return ceil(calidad.calcularCalidad(duracionDelVideo).toDouble()).toInt()
    }

    override fun cambiarTipoDePermiso(permiso: Permiso) {
      tipoPermiso = permiso
    }
  }


  abstract class Calidad {
    abstract fun calcularCalidad(segundos: Int): Int
  }

  class CalidadSd() : Calidad() {
    override fun calcularCalidad(segundos: Int) = segundos
  }

  open class Calidad720() : Calidad() {
    override fun calcularCalidad(segundos: Int) = segundos * 3
  }

  class Calidad1080() : Calidad720() {
    override fun calcularCalidad(segundos: Int) = super.calcularCalidad(segundos) * 2
  }

  abstract class Permiso() {
    abstract fun puedeVerPublicacion(amigo: Usuario): Boolean
  }


  class PermisoPublico() : Permiso() {
    override fun puedeVerPublicacion(amigo: Usuario) = true
  }

  class PermisoSoloAmigos(val amigos: List<Usuario>) : Permiso() {
    override fun puedeVerPublicacion(amigo: Usuario) = amigos.contains(amigo)
  }

  class PermisoPrivadoConListaDePermitidos(val listaDePermitidos: List<Usuario>) : Permiso() {
    override fun puedeVerPublicacion(amigo: Usuario) = listaDePermitidos.contains(amigo)
  }

  class PermisoPublicoConListaDeExcluidos(val listaDeExcluidos: List<Usuario>) : Permiso() {
    override fun puedeVerPublicacion(amigo: Usuario) = !listaDeExcluidos.contains(amigo)
  }
