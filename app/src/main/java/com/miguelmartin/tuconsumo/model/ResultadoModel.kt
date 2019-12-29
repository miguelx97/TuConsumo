package com.miguelmartin.tuconsumo.model

import android.content.Context
import com.miguelmartin.tuconsumo.Entities.Coche
import com.miguelmartin.tuconsumo.Entities.Resultados
import com.miguelmartin.tuconsumo.Entities.Viaje
import com.miguelmartin.tuconsumo.R
import com.miguelmartin.tuconsumo.db.PersistenciaCoche

class ResultadoModel {

    fun sumarRestarPasajeros(oldPasajeros: Int, cambio: Int): Int {
        if(oldPasajeros <= 1 && cambio<0) return 1
        return oldPasajeros + cambio
    }

    fun calcularCostoPorPasajero(costo: Double, pasajeros: Int): Double = Math.round(costo/pasajeros * 100.0) / 100.0

    fun generarTextoParaCompartir(resultados: Resultados,numPasajeros: Int, nombreApp:String) =
        "Se han recorrido ${resultados.distancia} Km.\n" +
        "El consumo ha costado ${resultados.costo}€.\n" +
        "Entre $numPasajeros pasajeros, ${calcularCostoPorPasajero(resultados.costo, numPasajeros)}€ cada uno.\n" +
        "***$nombreApp***"

    fun guardarCocheBd(coche: Coche, context: Context) = PersistenciaCoche(context).insert(coche)

    fun getResultados(viaje: Viaje): Resultados {
        //Datos del viaje
        val distanciaTrayecto = viaje.distanciaTrayecto
        val numViajes = viaje.numeroTrayectos
        val consumoCochePor100Km = viaje.coche.consumo
        val precioCombustible = viaje.coche.combustible.precio

        //Resultados
        val distanciaRecorrida = Math.round(distanciaTrayecto * numViajes * 100.0) / 100.0
        val combustibleGastado = Math.round(consumoCochePor100Km/100 * distanciaRecorrida * 100.0) / 100.0
        val coste = Math.round(combustibleGastado * precioCombustible * 100.0) / 100.0

        return Resultados(distanciaRecorrida, combustibleGastado, coste)
    }

    fun eliminarCocheBd(coche: Coche, context: Context):Boolean{
        val persistencia = PersistenciaCoche(context)
        if(coche.id != 0) return persistencia.eliminar(coche)
        else return persistencia.eliminarUltimoRegistro()
    }

    fun existeCoche(coche: Coche, context: Context) = PersistenciaCoche(context).existeCoche(coche)

}