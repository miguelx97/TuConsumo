package com.miguelmartin.consumii.presenter

import com.miguelmartin.consumii.Common.LISTA_COMUNIDADES
import com.miguelmartin.consumii.Enums.TipoCombustible
import com.miguelmartin.consumii.model.BienvenidaModel
import com.miguelmartin.consumii.view.BienvenidaActivity

class BienvenidaPresenter(view: BienvenidaActivity) {
    val view = view
    val model = BienvenidaModel(view)
    fun cargarCombustibles(){
        val arrNombresCombustibles = TipoCombustible.values().map { it.nombre }.toTypedArray()
        val arrNamesCombustibles = TipoCombustible.values()
        view.rellenarSpCombustiblesInicio(arrNombresCombustibles, arrNamesCombustibles)
    }
    fun cargarComunidades(){
        val arrComunidades = LISTA_COMUNIDADES.values.toTypedArray()
        arrComunidades.sort()
        view.rellenarSpComunidadesInicio(arrComunidades)
    }

    fun ocBtnAceptar(){
        val datosUsuario = view.getDatos()
        model.guardarComunidadPrefferences(datosUsuario.comunidad)
        model.guardarCocheBd(datosUsuario.coche)
        view.irMain()

    }
}