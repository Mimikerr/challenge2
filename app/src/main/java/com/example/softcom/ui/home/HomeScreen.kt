package com.example.softcom.ui.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.softcom.R
import com.example.softcom.data.entities.Produto
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel) {
    val filteredState by viewModel.filteredProdutos.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val appContext = LocalContext.current
    val searchQuery by viewModel.searchQuery.collectAsState()

    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFF6600))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Pet Friends Accessories",
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 16.dp, vertical = 1.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search Icon",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextField(
                        value = searchQuery,
                        onValueChange = { query -> viewModel.updateSearchQuery(query) },
                        placeholder = { Text("O que você procura?") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        },
        bottomBar = {
            Column {
                if (cartItems.isNotEmpty()) {
                    Button(
                        onClick = { navController.navigate("cart") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF6600))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_carrinho),
                                contentDescription = "Carrinho",
                                tint = Color.White
                            )
                            Text(
                                text = "Ver Carrinho",
                                color = Color.White,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Text(
                                text = "R$ ${String.format("%.2f", cartItems.sumOf { it.preco })}",
                                color = Color.White,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }

                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val categories = listOf(
                        "Camas" to R.drawable.ic_cama,
                        "Brinquedos" to R.drawable.ic_brinquedos,
                        "Comedouros" to R.drawable.ic_comedouro,
                        "Casinhas" to R.drawable.ic_casa
                    )

                    items(categories) { (categoria, imageRes) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    selectedCategory = if (selectedCategory == categoria) null else categoria
                                    viewModel.updateSearchQuery("")
                                }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (selectedCategory == categoria) Color.White else Color(0xFFFF6600),
                                        shape = CircleShape
                                    )
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = imageRes),
                                    contentDescription = categoria,
                                    modifier = Modifier.size(32.dp),
                                    tint = if (selectedCategory == categoria) Color(0xFFFF6600) else Color.White
                                )
                            }
                            Text(
                                text = categoria,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedCategory == categoria) Color(0xFFFF6600) else Color.Black
                            )
                        }
                    }
                }
            }

            val displayedProducts = if (selectedCategory != null) {
                filteredState.filter { it.categoria == selectedCategory }
            } else {
                filteredState
            }

            displayedProducts.groupBy { it.categoria }.forEach { (categoria, produtos) ->
                item {
                    Text(
                        text = categoria,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 8.dp)
                    )
                }

                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(produtos) { produto ->
                            ProdutoCard(
                                context = appContext,
                                produto = produto,
                                viewModel = viewModel,
                                snackbarHostState = snackbarHostState,
                                onClick = {
                                    navController.navigate("produtoDetalhes/${produto.id}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}





@Composable
fun ProdutoCard(
    context: Context,
    produto: Produto,
    viewModel: HomeViewModel,
    snackbarHostState: SnackbarHostState,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .aspectRatio(0.8f)
            .padding(8.dp)
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Box {
                if (produto.isOnSale) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF4CAF50), shape = MaterialTheme.shapes.small)
                            .align(Alignment.TopStart)
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

                Image(
                    painter = painterResource(id = produto.imagemRes),
                    contentDescription = produto.nome,
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = produto.nome,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (produto.isOnSale) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "De R$ ${produto.originalPrice}",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Text(
                        text = "Por R$ ${produto.discountedPrice}",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6600)
                    )
                }
            } else {
                Text(
                    text = "R$ ${produto.preco}",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6600)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            IconButton(
                onClick = {
                    viewModel.addToCart(produto)

                    viewModel.viewModelScope.launch {
                        snackbarHostState.showSnackbar("Adicionado ao carrinho com sucesso!")
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_carrinho),
                    contentDescription = "Adicionar",
                    modifier = Modifier.size(18.dp),
                    tint = Color(0xFFFF6600)
                )
            }
        }
    }
}



@Composable
fun BottomNavigationBar(navController: NavHostController,) {
    val items = listOf(
        BottomNavItem("Home", R.drawable.ic_home),
        BottomNavItem("Pedidos", R.drawable.ic_orders),
        BottomNavItem("Mais", R.drawable.ic_more)
    )

    var selectedItem by remember { mutableStateOf(0) }

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Gray
    ) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.label,
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                },
                selected = selectedItem == index,
                onClick = { 
                    selectedItem = index
                    if (item.label == "Pedidos") {
                        navController.navigate("cart")
                    }

            
            },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray
            )
        }
    }
}

data class BottomNavItem(val label: String, val icon: Int)
