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
        val josefa = Usuario()

        bienvenida.agregarLike(pedro)
        bienvenida.agregarLike(josefa)

        bienvenida.cantidadDeLikes().shouldBe(2)
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



    describe("Caralibro saber si un usuario no es mas amistoso que otro") {
      it("Usuario no es mas amistoso que otro") {

        val pedro = Usuario()
        val mirta = Usuario()
        val jose = Usuario()
        val federica = Usuario()

        pedro.agregarAmigo(mirta)
        pedro.agregarAmigo(jose)
        pedro.agregarAmigo(federica)

        federica.agregarAmigo(jose)

        val caralibro = Caralibro()
        caralibro.agregarUsuario(pedro)
        caralibro.agregarUsuario(federica)

        caralibro.saberSiUnUsuarioEsMasAmistosoQueOtro(federica,pedro).shouldBe(false)


      }
    }

    describe("Caralibro saber si un usuario es mas amistoso que otro") {
      it("Usuario que si es mas amistoso que otro") {

        val pedro = Usuario()
        val mirta = Usuario()
        val jose = Usuario()
        val federica = Usuario()

        pedro.agregarAmigo(mirta)
        pedro.agregarAmigo(jose)
        pedro.agregarAmigo(federica)

        federica.agregarAmigo(jose)

        val caralibro = Caralibro()
        caralibro.agregarUsuario(pedro)
        caralibro.agregarUsuario(federica)

        caralibro.saberSiUnUsuarioEsMasAmistosoQueOtro(pedro,federica).shouldBe(true)


      }
    }


    describe("Saber si un usuario puede ver cierta publicacion") {
      it("Usuario puede ver cierta publicacion") {

        val pedro = Usuario()
        val mirta = Usuario()
        val jose = Usuario()
        val federica = Usuario()

        pedro.agregarAmigo(mirta)
        pedro.agregarAmigo(jose)
        pedro.agregarAmigo(federica)

        federica.agregarAmigo(jose)


        var textoSaludo = Texto("Hola", PermisoSoloAmigos(federica.listaDeAmigos))

        federica.agregarPublicacion(textoSaludo)

        federica.puedeVerPublicacion(jose,textoSaludo).shouldBe(true)




      }


      it("Usuario no  puede ver cierta publicacion") {

        val pedro = Usuario()
        val mirta = Usuario()
        val jose = Usuario()
        val federica = Usuario()

        pedro.agregarAmigo(mirta)
        pedro.agregarAmigo(jose)
        pedro.agregarAmigo(federica)

        federica.agregarAmigo(jose)


        var textoSaludo = Texto("Hola", PermisoSoloAmigos(federica.listaDeAmigos))

        federica.agregarPublicacion(textoSaludo)

        federica.puedeVerPublicacion(mirta,textoSaludo).shouldBe(false)




      }

    }



    describe("Saber el amigo mas popular") {
      it("Saber el amigo mas popular") {

        val pedro = Usuario()
        val mirta = Usuario()
        val jose = Usuario()
        val federica = Usuario()

        val foto = Foto(120,220,PermisoPublico())
        foto.agregarLike(mirta)
        foto.agregarLike(jose)
        foto.agregarLike(federica)

        pedro.agregarPublicacion(foto)

        pedro.agregarAmigo(mirta)
        pedro.agregarAmigo(jose)
        pedro.agregarAmigo(federica)

        federica.agregarAmigo(jose)
        federica.agregarAmigo(pedro)

        federica.amigoMasPopular().shouldBe(pedro)
      }
    }


    describe("Determinar los mejores amigos de un usuario") {
      it("Lista de mejores amigos") {

        val pedro = Usuario()
        val mirta = Usuario()
        val jose = Usuario()
        val federica = Usuario()

        val foto = Foto(120,220,PermisoPublico())

        foto.agregarLike(mirta)
        foto.agregarLike(jose)
        foto.agregarLike(federica)

        pedro.agregarPublicacion(foto)

        pedro.agregarAmigo(mirta)
        pedro.agregarAmigo(jose)
        pedro.agregarAmigo(federica)

        federica.agregarAmigo(jose)
        federica.agregarAmigo(pedro)

        pedro.determinarLosMejoresAmigos().count().shouldBe(3)


      }
    }

    describe("Amigo mas popular") {
      it("Encontrar el amigo mas popular de un usuario") {

        val pedro = Usuario()
        val mirta = Usuario()
        val jose = Usuario()
        val federica = Usuario()

        val foto = Foto(120,220,PermisoPublico())
        val videoSD = Video(10, CalidadSd(), tipoPermiso = PermisoPublico())

        foto.agregarLike(mirta)
        foto.agregarLike(jose)
        foto.agregarLike(federica)

        videoSD.agregarLike(jose)

        //pedroo cantida d likes 3
        pedro.agregarPublicacion(foto)

        pedro.agregarAmigo(mirta)
        pedro.agregarAmigo(jose)
        pedro.agregarAmigo(federica)

        //Federica cantidad de likes 2
        federica.agregarAmigo(jose)
        federica.agregarAmigo(pedro)
        federica.agregarPublicacion(videoSD)

        mirta.agregarAmigo(pedro)
        mirta.agregarAmigo(federica)

        mirta.amigoMasPopular().shouldBe(pedro)
       // mirta.amigoMasPopular().shouldBe(federica)


      }
    }


    describe("Amigo stalkea a otro") {
      it("Saber si un uruario stakea  a otro, esto es si likeo al 90% de las publicaciones En este caso SI ") {

        val pedro = Usuario()
        val mirta = Usuario()
        val jose = Usuario()
        val federica = Usuario()

        val foto1 = Foto(120,220,PermisoPublico())
        val videoSD = Video(10, CalidadSd(), tipoPermiso = PermisoPublico())

        val foto2 = Foto(120,220,PermisoPublico())
        val foto3 = Foto(120,220,PermisoPublico())
        val foto4 = Foto(120,220,PermisoPublico())
        val foto5 = Foto(120,220,PermisoPublico())
        val foto6 = Foto(120,220,PermisoPublico())
        val foto7 = Foto(120,220,PermisoPublico())
        val foto8 = Foto(120,220,PermisoPublico())
        val foto9 = Foto(120,220,PermisoPublico())


        foto1.agregarLike(mirta)
        foto2.agregarLike(mirta)
        foto3.agregarLike(mirta)
        foto4.agregarLike(mirta)
        foto5.agregarLike(mirta)
        foto6.agregarLike(mirta)
        foto7.agregarLike(mirta)
        foto8.agregarLike(mirta)
        foto9.agregarLike(mirta)
      //  videoSD.agregarLike(mirta)
        videoSD.agregarLike(jose)

        //pedroo cantida d likes 3
        pedro.agregarPublicacion(foto1)
        pedro.agregarPublicacion(foto2)
        pedro.agregarPublicacion(foto3)
        pedro.agregarPublicacion(foto4)
        pedro.agregarPublicacion(foto5)
        pedro.agregarPublicacion(foto6)
        pedro.agregarPublicacion(foto7)
        pedro.agregarPublicacion(foto8)
        pedro.agregarPublicacion(foto9)
        pedro.agregarPublicacion(videoSD)

        pedro.agregarAmigo(mirta)
        pedro.agregarAmigo(jose)
        pedro.agregarAmigo(federica)

        pedro.usuarioStalkeaAmigo(mirta).shouldBe(true)
      }


      it("Saber si un uruario stakea  a otro, esto es si likeo al 90% de las publicaciones. En este caso NO ") {

        val pedro = Usuario()
        val mirta = Usuario()
        val jose = Usuario()
        val federica = Usuario()

        val foto1 = Foto(120,220,PermisoPublico())
        val videoSD = Video(10, CalidadSd(), tipoPermiso = PermisoPublico())

        val foto2 = Foto(120,220,PermisoPublico())
        val foto3 = Foto(120,220,PermisoPublico())
        val foto4 = Foto(120,220,PermisoPublico())
        val foto5 = Foto(120,220,PermisoPublico())
        val foto6 = Foto(120,220,PermisoPublico())
        val foto7 = Foto(120,220,PermisoPublico())
        val foto8 = Foto(120,220,PermisoPublico())
        val foto9 = Foto(120,220,PermisoPublico())


        foto1.agregarLike(mirta)
        foto2.agregarLike(mirta)
        foto3.agregarLike(mirta)
       //foto4.agregarLike(mirta)
       //foto5.agregarLike(mirta)
       //foto6.agregarLike(mirta)
       //foto7.agregarLike(mirta)
       //foto8.agregarLike(mirta)
       //foto9.agregarLike(mirta)
        //  videoSD.agregarLike(mirta)
        videoSD.agregarLike(jose)

        //pedroo cantida d likes 3
        pedro.agregarPublicacion(foto1)
        pedro.agregarPublicacion(foto2)
        pedro.agregarPublicacion(foto3)
        pedro.agregarPublicacion(foto4)
        pedro.agregarPublicacion(foto5)
        pedro.agregarPublicacion(foto6)
        pedro.agregarPublicacion(foto7)
        pedro.agregarPublicacion(foto8)
        pedro.agregarPublicacion(foto9)
        pedro.agregarPublicacion(videoSD)

        pedro.agregarAmigo(mirta)
        pedro.agregarAmigo(jose)
        pedro.agregarAmigo(federica)

        pedro.usuarioStalkeaAmigo(mirta).shouldBe(false)



      }
    }

  }
})
