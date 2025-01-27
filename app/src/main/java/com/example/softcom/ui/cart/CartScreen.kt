package com.example.softcom.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.softcom.R
import com.example.softcom.data.entities.Produto
import com.example.softcom.ui.home.HomeViewModel

@Composable
fun CartScreen(navController: NavController, viewModel: HomeViewModel) {
    val cartItems by viewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrinho", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = Color(0xFFFF6600)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(cartItems) { produto ->
                    CartItemRow(
                        produto = produto,
                        onRemoveItem = { viewModel.removeFromCart(produto) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            val totalPrice = cartItems.sumOf { it.preco }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total: R$ ${"%.2f".format(totalPrice)}",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6600)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /*  */ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF6600)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Finalizar Compra", color = Color.White)
                }
            }
        }
    }
}


@Composable
fun CartItemRow(produto: Produto, onRemoveItem: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = produto.imagemRes),
                contentDescription = produto.nome,
                modifier = Modifier
                    .size(64.dp)
                    .padding(4.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = produto.nome,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1
                )
                Text(
                    text = "R$ ${"%.2f".format(produto.preco)}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
            IconButton(onClick = onRemoveItem) {
                Icon(
                    painter = painterResource(id = com.example.softcom.R.drawable.ic_remove),
                    contentDescription = "Remover",
                    modifier = Modifier.size(28.dp),
                    tint = Color.Red
                )
            }
        }
    }
}
