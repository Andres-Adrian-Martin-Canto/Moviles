package com.example.proyectofinal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Productos")
class Producto {
    /*
    Creo llave primaria que se llamara
    idProduct y lo instancio a 0 para
    que empiece asi.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idProduct")
    var id:Int = 0

    /*
    Creo la segunda columna que es la que tendra
    el nombre del producto.
     */
    @ColumnInfo(name = "nameProducto")
    var productName:String?=null

    /*
    Creo la tercera columna que tendra el precio
    del producto
     */
    @ColumnInfo(name = "precioProducto")
    var precioProduct:Int = 0

    /*
    Creo la cuarta columna que tendra la cantidad
    del producto
     */
    @ColumnInfo(name = "cantidadProducto")
    var cantidadProducto:Int = 0

    /*
    Constructor que nos ayudara
     */
    constructor(){}
    

    // Constructor para crear un objeto
    constructor(productName:String, precioProduct:Int, cantidad:Int){
        this.productName = productName
        this.precioProduct = precioProduct
        this.cantidadProducto = cantidad
    }


}