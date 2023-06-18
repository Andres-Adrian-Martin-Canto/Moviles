package com.example.proyectofinal

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class ProductRepository(application: Application){
    val buscarProducto = MutableLiveData<List<Producto>>()
    private var productDao:ProductDao?
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    // Aqui almacenare todos los productos en una lista
    val todoLosProductos: LiveData<List<Producto>>?

    init {
        // Obtenemos la base de datos
        val db: DataBase? = DataBase.getDatabase(application)
        // Inicializamos la base de datos que viene
        productDao=db?.productDao()
        // Le enviamos todos
        todoLosProductos =productDao?.obtenerTodosProductos()
    }

    fun insertProduct(newProduct: Producto){
        coroutineScope.launch(Dispatchers.IO){
            asyncInsert(newProduct)
        }
    }

    private fun asyncInsert(product: Producto){
        productDao?.insertarProducto(product)
    }

    fun deleteProduct(name:String){
        coroutineScope.launch(Dispatchers.IO){
            asyncDelete(name)
        }
    }

    private fun asyncDelete(name:String){
        productDao?.deleteProduct(name)
    }

    fun findProduct(name:String){
        coroutineScope.launch(Dispatchers.Main){
            buscarProducto.value = asyncFind(name).await()
        }
    }

    private fun asyncFind(name:String): Deferred<List<Producto>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async productDao?.findProduct(name)
        }

}