import java.util.*

// Pueden usar este archivo para hacer pruebas rápidas,
// de la misma forma en que usaban el REPL de Wollok.

// OJO: lo que esté aquí no será tenido en cuenta
// en la corrección ni reemplaza a los tests.

listOf(1, 8, 10).average()


class Numero(){
    var  id = UUID.randomUUID()
}

val uno = Numero()
val dos = Numero()
val tres = Numero()

val lista = mutableListOf<Numero>()
lista.add(uno)
lista.add(dos)

lista.contains(uno)

println("Existe? " + lista.find { it.id == tres.id }?.id.toString())




