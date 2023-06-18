package com.example.proyectofinal.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyectofinal.ProductRepository
import com.example.proyectofinal.Producto

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repositorio: ProductRepository = ProductRepository(application)
    private val todosLosProductos: LiveData<List<Producto>>?
    private val buscarProducto:MutableLiveData<List<Producto>>

    init {
        todosLosProductos =repositorio.todoLosProductos
        buscarProducto = repositorio.buscarProducto
    }

    fun buscar(name: String) {
        repositorio.findProduct(name)
    }

    fun insertProduct(product: Producto) {
        repositorio.insertProduct(product)
    }

    fun deleteProduct(name: String) {
        repositorio.deleteProduct(name)
    }
    fun getSearchResults(): MutableLiveData<List<Producto>> {
        return buscarProducto
    }

    fun obtenerProductos(): LiveData<List<Producto>>? {
        return todosLosProductos
    }

}