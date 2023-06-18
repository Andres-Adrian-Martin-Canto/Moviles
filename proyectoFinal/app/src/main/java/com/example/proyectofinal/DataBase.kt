package com.example.proyectofinal

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import android.content.Context

// Declaramos la version
@Database(entities = [(Producto::class)], version = 1)
abstract class DataBase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    // La base de datos.
    companion object {
        private var INSTANCE: DataBase? = null
        internal fun getDatabase(context: Context): DataBase? {
            if (INSTANCE == null) {
                synchronized(DataBase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            DataBase::class.java,"listaMercado").build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
