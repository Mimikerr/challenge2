package com.example.softcom.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.softcom.data.dao.ProdutoDao
import com.example.softcom.data.entities.Produto

@Database(entities = [Produto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao
}

