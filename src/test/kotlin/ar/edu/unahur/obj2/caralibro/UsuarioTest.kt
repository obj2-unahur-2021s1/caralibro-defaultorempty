package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)
    val videoSD = Video(10,CalidadSd())
    val video720 = Video(10,Calidad720())
    val video1080 = Video(10,Calidad1080())

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
        juana.espacioDePublicaciones().shouldBe(550548)
      }
    }
  }
})
