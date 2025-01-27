package com.example.softcom.repository

import com.example.softcom.R
import com.example.softcom.data.entities.Produto

class ProdutoRepository {

    fun getProdutos(): List<Produto> {
        return listOf(
            Produto(
                id = 1,
                nome = "Cama Preta para pet pequeno",
                descricao = "Cama macia para pequenos amiguinhos",
                preco = 139.90,
                originalPrice = 295.90,
                isOnSale = true,
                discountedPrice = 266.30,
                discountPercentage = 10,
                categoria = "Camas",
                imagemRes = R.drawable.produto1
            ),
            Produto(
                id = 2,
                nome = "Cama Cinza para pet de grande porte",
                descricao = "Cama macia para grandes amiguinhos",
                preco = 199.99,
                categoria = "Camas",
                imagemRes = R.drawable.produto2
            ),
            Produto(
                id = 3,
                nome = "Cama felpuda média",
                descricao = "Cama felpuda para pet's de porte médio",
                preco = 159.99,
                categoria = "Camas",
                imagemRes = R.drawable.produto3
            ),
            Produto(
                id = 4,
                nome = "Osso mordedor azul",
                descricao = "Brinquedo para seu pet se divertir",
                preco = 29.99,
                categoria = "Brinquedos",
                imagemRes = R.drawable.produto4
            ),
            Produto(
                id = 5,
                nome = "Disco Rosa",
                descricao = "Brinquedo para seu pet se divertir",
                preco = 39.99,
                isOnSale = true,
                originalPrice = 39.99,
                discountedPrice = 35.91,
                discountPercentage = 10,
                categoria = "Brinquedos",
                imagemRes = R.drawable.produto5
            ),
            Produto(
                id = 6,
                nome = "Osso Morderdor Laranja",
                descricao = "Brinquedo para seu pet se divertir",
                preco = 19.99,
                categoria = "Brinquedos",
                imagemRes = R.drawable.produto6
            )
        )
    }
}
