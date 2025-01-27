package com.example.softcom.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Produto(
    @PrimaryKey val id: Int,
    val nome: String,
    val descricao: String,
    val preco: Double,
    val isOnSale: Boolean = false,
    val originalPrice: Double = 0.0,
    val discountedPrice: Double = 0.0,
    val discountPercentage: Int = 0,
    val categoria: String,
    val imagemRes: Int
) : Parcelable
