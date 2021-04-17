package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion {
  abstract fun espacioQueOcupa(): Int
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
  var factorDeCompresion = 0.7
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt()

  fun cambiarFactorDeCompresion( nuevoFactorDeCompresion: Double)
  {
    factorDeCompresion = nuevoFactorDeCompresion
  }
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Video(val duracionDelVideo : Int, val calidad : Calidad) : Publicacion() {

  override fun espacioQueOcupa(): Int {
    return ceil(calidad.calcularCalidad(duracionDelVideo).toDouble()).toInt()
  }
}


abstract class Calidad
{
abstract fun calcularCalidad(segundos : Int) : Int
}

class CalidadSd() : Calidad()
{
  override fun calcularCalidad(segundos : Int) = segundos
}

open class Calidad720() : Calidad()
{
  override fun calcularCalidad(segundos : Int) = segundos * 3
}

class Calidad1080() : Calidad720()
{
  override fun calcularCalidad(segundos : Int) = super.calcularCalidad(segundos) * 2
}

