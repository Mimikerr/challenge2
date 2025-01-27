package com.example.softcom.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softcom.data.entities.Produto
import com.example.softcom.repository.ProdutoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val produtoRepository = ProdutoRepository()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _produtos = MutableStateFlow<List<Produto>>(emptyList())
    val produtos: StateFlow<List<Produto>> = _produtos

    private val _cartItems = MutableStateFlow<List<Produto>>(emptyList())
    val cartItems: StateFlow<List<Produto>> = _cartItems

    val filteredProdutos: StateFlow<List<Produto>> = combine(_produtos, _searchQuery) { produtos, query ->
        if (query.isBlank()) {
            produtos
        } else {
            produtos.filter { it.nome.contains(query, ignoreCase = true) }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadProdutos()
    }

    private fun loadProdutos() {
        viewModelScope.launch {
            val produtosSimulados = produtoRepository.getProdutos()
            _produtos.value = produtosSimulados
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addToCart(produto: Produto) {
        val currentCart = _cartItems.value.toMutableList()
        currentCart.add(produto)
        _cartItems.value = currentCart
    }

    fun removeFromCart(produto: Produto) {
        val currentCart = _cartItems.value.toMutableList()
        currentCart.remove(produto)
        _cartItems.value = currentCart
    }
}
