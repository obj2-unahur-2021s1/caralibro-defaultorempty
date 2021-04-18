package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.shouldFail
import io.kotest.assertions.throwables.shouldThrowAny

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios =
      Texto("Felicidades Pepito, que los cumplas muy feliz", tipoPermiso = PermisoPublico())
    val fotoEnCuzco = Foto(768, 1024, tipoPermiso = PermisoPublico())
    val videoSD = Video(10, CalidadSd(), tipoPermiso = PermisoPublico())
    val video720 = Video(10, Calidad720(), tipoPermiso = PermisoPublico())
    val video1080 = Video(10, Calidad1080(), tipoPermiso = PermisoPublico())

    describe("Una publicación") {
      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)
        }
      }

      describe("de tipo texto") {
        it("ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldBe(45)
        }
      }

      describe("de tipo Video Sd") {
        it("ocupa tantos bytes como su tiempo de duracion") {
          videoSD.espacioQueOcupa().shouldBe(10)
        }
      }

      describe("de tipo Video 720p") {
        it("ocupa tantos bytes como su tiempo de duracion por 3") {
          video720.espacioQueOcupa().shouldBe(30)
        }
      }

      describe("de tipo Video 1080p") {
        it("ocupa tantos bytes como su tiempo de duracion el doble de uno 720p") {
          video1080.espacioQueOcupa().shouldBe(60)
        }
      }


    }

    describe("Un usuario") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
        val juana = Usuario()
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
        juana.agregarPublicacion(videoSD)
        juana.agregarPublicacion(video720)
        juana.agregarPublicacion(video1080)

        //juana.espacioDePublicaciones().shouldBe(550548) sin videos
        juana.espacioDePublicaciones().shouldBe( 550648) // con videos 550548 + 10 + 30 + 60
      }
    }

    describe("Un usuario con publicaciones y likes") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
        val juana = Usuario()
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
        juana.agregarPublicacion(videoSD)
        juana.agregarPublicacion(video720)
        juana.agregarPublicacion(video1080)

        //juana.espacioDePublicaciones().shouldBe(550548) sin videos
        juana.espacioDePublicaciones().shouldBe( 550648) // con videos 550548 + 10 + 30 + 60
      }
    }

    describe("Una publicaciones y likes") {
      it("Texto y me gusta de un usuario") {
        val bienvenida = Texto("Bienvenidos",tipoPermiso = PermisoPublico() )
        val pedro = Usuario()

        bienvenida.agregarLike(pedro)

        bienvenida.cantidadDeLikes().shouldBe(1)
      }
    }

    describe("Una publicaciones y 2 likes del mismo usuario") {
      it("Texto y me gusta de un usuario") {
        val bienvenida = Texto("Bienvenidos",tipoPermiso = PermisoPublico() )
        val pedro = Usuario()

        val exception = shouldThrowAny {
          bienvenida.agregarLike(pedro)
          bienvenida.agregarLike(pedro)
        }

      }
    }

  }
})
