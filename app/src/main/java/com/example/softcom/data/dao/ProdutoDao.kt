package com.example.softcom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.softcom.data.entities.Produto

@Dao
interface ProdutoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirProduto(produto: Produto)

    @Query("SELECT * FROM Produto")
    fun listarProdutos(): List<Produto>
}
