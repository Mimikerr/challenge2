package com.example.softcom.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.softcom.data.dao.ProdutoDao
import com.example.softcom.data.entities.Produto

@Database(entities = [Produto::class], version = 1, exportSchema = false)
abstract class DatabaseInstance : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseInstance? = null

        fun getDatabase(context: Context): DatabaseInstance {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseInstance::class.java,
                    "loja_virtual_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
