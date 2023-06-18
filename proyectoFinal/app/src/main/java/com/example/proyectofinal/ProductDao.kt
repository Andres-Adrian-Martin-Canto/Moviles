package com.example.proyectofinal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyectofinal.Producto

@Dao
interface ProductDao {

    /*
    Consulta que me ayudara a inserta
    datos a mi tabla
     */
    @Insert
    fun insertarProducto(producto: Producto)

    /*
    Consulta que me ayudara a obtener todo los valores
    que estan en la tabla para luego agregarlos en el
    recyclerView.
     */
    @Query("SELECT * FROM productos")
    fun obtenerTodosProductos():LiveData<List<Producto>>

    /*
    Consulta para eliminar un producto
     */
    @Query("DELETE FROM Productos WHERE nameProducto= :name")
    fun deleteProduct(name: String)

    /*
    Consulta que busca un valor en especifico
     */
    @Query("SELECT * FROM Productos WHERE nameProducto= :name")
    fun findProduct(name:String):List<Producto>


}