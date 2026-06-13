package com.example.tugas11_marketsiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.text.NumberFormat
import androidx.compose.ui.graphics.Color
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudentShopApp()
        }
    }
}
data class Product(
    val name: String,
    val price: Int,
    val category: String
)

@Composable
fun StudentShopApp() {

    val products = remember { mutableStateListOf<Product>() }
    var page by remember { mutableStateOf(0) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(

        containerColor = Color(0xFFF5F7FF),

        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },

        floatingActionButton = {

            if (page == 0) {

                FloatingActionButton(
                    containerColor = Color(0xFFFF9800),
                    contentColor = Color.White,
                    onClick = {
                        page = 1
                    }
                ) {
                    Icon(Icons.Default.Add, null)
                }
            }
        },

        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = page == 0,
                    onClick = { page = 0 },
                    icon = {
                        Icon(Icons.Default.Home, null)
                    },
                    label = {
                        Text("Beranda")
                    }
                )

                NavigationBarItem(
                    selected = page == 2,
                    onClick = { page = 2 },
                    icon = {
                        Icon(Icons.Default.Person, null)
                    },
                    label = {
                        Text("Profil")
                    }
                )
            }
        }

    ) { padding ->

        when (page) {

            // DASHBOARD
            0 -> {

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .fillMaxSize()
                ) {

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF00C2FF)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {

                            Text(
                                "Halo Siswa 👋",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text(
                                "Kelola produk jualanmu dengan mudah",
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF6C63FF)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {

                            Text(
                                "Total Produk",
                                color = Color.White
                            )

                            Text(
                                "${products.size}",
                                color = Color.White,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (products.isEmpty()) {

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                "📦 Belum ada produk"
                            )
                        }

                    } else {

                        LazyColumn {

                            items(products) { product ->

                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFE8EAF6)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                ) {

                                    Column(
                                        modifier = Modifier.padding(16.dp)
                                    ) {

                                        Text(
                                            "🛍️ ${product.name}",
                                            fontWeight = FontWeight.Bold
                                        )

                                        Text(
                                            NumberFormat
                                                .getCurrencyInstance(
                                                    Locale("id", "ID")
                                                )
                                                .format(product.price)
                                        )

                                        Text(
                                            "Kategori : ${product.category}"
                                        )

                                        Spacer(
                                            modifier = Modifier.height(8.dp)
                                        )

                                        Button(
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.Red
                                            ),
                                            onClick = {

                                                products.remove(product)

                                                scope.launch {
                                                    snackbarHostState.showSnackbar(
                                                        "Produk berhasil dihapus"
                                                    )
                                                }
                                            }
                                        ) {
                                            Text("Hapus")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // TAMBAH PRODUK
            1 -> {

                var nama by remember { mutableStateOf("") }
                var harga by remember { mutableStateOf("") }
                var kategori by remember { mutableStateOf("") }

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .fillMaxSize()
                ) {

                    Text(
                        "Tambah Produk",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = nama,
                        onValueChange = { nama = it },
                        label = {
                            Text("Nama Produk")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = harga,
                        onValueChange = {
                            harga = it.filter { c ->
                                c.isDigit()
                            }
                        },
                        label = {
                            Text("Harga")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = kategori,
                        onValueChange = {
                            kategori = it
                        },
                        label = {
                            Text("Kategori")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6C63FF)
                        ),

                        enabled =
                            nama.length >= 3 &&
                                    harga.isNotEmpty() &&
                                    kategori.isNotEmpty(),

                        onClick = {

                            products.add(
                                Product(
                                    nama,
                                    harga.toInt(),
                                    kategori
                                )
                            )

                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Produk berhasil ditambahkan"
                                )
                            }

                            page = 0
                        }
                    ) {

                        Text("Simpan Produk")
                    }
                }
            }

            // PROFIL
            2 -> {

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(20.dp)
                        .fillMaxSize(),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF6C63FF)
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                "👨‍🎓",
                                style = MaterialTheme.typography.displayMedium
                            )

                            Text(
                                "Nama Siswa",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text(
                                "Informatika",
                                color = Color.White
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Card {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                "Produk Aktif : ${products.size}"
                            )

                            Text(
                                "Status : Penjual Aktif ⭐"
                            )
                        }
                    }
                }
            }
        }
    }
}