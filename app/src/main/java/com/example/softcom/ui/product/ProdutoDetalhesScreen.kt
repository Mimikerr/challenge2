package com.example.softcom.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.softcom.data.entities.Produto
import com.example.softcom.ui.home.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductDetailsScreen(
    navController: NavController,
    produto: Produto?,
    viewModel: HomeViewModel
) {
    if (produto == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Produto não encontrado.",
                color = Color.Red,
                style = MaterialTheme.typography.h6
            )
        }
    } else {
        val snackbarHostState = remember { SnackbarHostState() }

        var quantidade by remember { mutableStateOf(1) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Detalhes do Produto", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                painter = painterResource(id = com.example.softcom.R.drawable.ic_back),
                                contentDescription = "Voltar",
                                tint = Color.White
                            )
                        }
                    },
                    backgroundColor = Color(0xFFFF6600)
                )
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopStart
                ) {
                    Image(
                        painter = painterResource(id = produto.imagemRes),
                        contentDescription = produto.nome,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                    if (produto.isOnSale) {
                        Box(
                            modifier = Modifier
                                .background(Color(0xFF4CAF50), shape = RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${produto.discountPercentage}% OFF",
                                fontSize = 12.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = produto.nome,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6600)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = produto.descricao,
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { if (quantidade > 1) quantidade-- }) {
                            Icon(
                                painter = painterResource(id = com.example.softcom.R.drawable.ic_minus),
                                contentDescription = "Diminuir",
                                tint = Color(0xFFFF6600)
                            )
                        }
                        Text(
                            text = "$quantidade",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        IconButton(onClick = { quantidade++ }) {
                            Icon(
                                painter = painterResource(id = com.example.softcom.R.drawable.ic_plus),
                                contentDescription = "Aumentar",
                                tint = Color(0xFFFF6600)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(horizontalAlignment = Alignment.End) {
                        if (produto.isOnSale && produto.originalPrice != null) {
                            Text(
                                text = "De R$ ${String.format("%.2f", produto.originalPrice)}",
                                style = MaterialTheme.typography.body2,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                        Text(
                            text = "Por R$ ${String.format("%.2f", produto.preco * quantidade)}",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF6600)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            repeat(quantidade) {
                                viewModel.addToCart(produto)
                            }

                            viewModel.viewModelScope.launch {
                                snackbarHostState.showSnackbar("Adicionado ao carrinho com sucesso!")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF6600))
                    ) {
                        Text(text = "Adicionar", color = Color.White)
                    }
                }
            }
        }
    }
}
