package com.ucne.yokairataveras_ap2_p1.presentation.Servicio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.yokairataveras_ap2_p1.data.local.entities.ServicioEntity
import com.ucne.yokairataveras_ap2_p1.presentation.components.FloatingButton
import com.ucne.yokairataveras_ap2_p1.presentation.components.TopAppBar
import com.ucne.yokairataveras_ap2_p1.ui.theme.YokairaTaveras_AP2_P1Theme

@Composable
fun ServicioListScreen(
    viewModel: ServicioViewModel,
    onVerServicio: (ServicioEntity) -> Unit,
    onAddServicio: () -> Unit,
    openDrawer: () -> Unit,
) {
    val servicios by viewModel.servicio.collectAsStateWithLifecycle()
    ServicioListBody(
        servicios = servicios,
        onVerServicio = onVerServicio,
        onEliminarServicio = { viewModel.deleteServicio() },
        onAddServicio = onAddServicio,
        openDrawer = openDrawer
    )
}

@Composable
fun ServicioListBody(
    servicios: List<ServicioEntity>,
    onVerServicio: (ServicioEntity) -> Unit,
    openDrawer: () -> Unit,
    onEliminarServicio: () -> Unit,
    onAddServicio: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = "Lista de Servicios",
                openDrawer = openDrawer
            )
        },
        floatingActionButton = {
            FloatingButton(onAddServicio)
        }
    ) { innerPadding ->
        var showDitailsDialog by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf<ServicioEntity?>(null) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text ="Id", modifier = Modifier.weight(0.10f))
                Text(text = "Descripción", modifier = Modifier.weight(0.30f))
                Text(text = "Precio", modifier = Modifier.weight(0.20f))
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(servicios) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedItem = item
                                showDitailsDialog = true
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(text = item.servicioId.toString(), modifier = Modifier.weight(0.10f))
                        Text(text = item.descripcion?: "", modifier = Modifier.weight(0.300f))
                        Text(text = item.precio.toString(), modifier = Modifier.weight(0.20f))
                    }
                }
            }

        }

        if (showDitailsDialog) {
            AlertDialog(
                onDismissRequest = { showDitailsDialog = false },
                title = {
                    Text(text = "Sevicio seleccionado")
                },
                text = {
                    Column{

                        Row{
                            Text("Descripción del servicio: ")
                            Text("${selectedItem?.descripcion}")
                        }
                        Row{
                            Text("Precio: ")
                            Text(selectedItem?.precio.toString())
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onVerServicio(selectedItem?: ServicioEntity())
                            showDitailsDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Editar")
                        Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDitailsDialog = false }) {
                        Text(text = "Cancelar")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun ServicioListPreview() {
    val servicios = listOf(
        ServicioEntity(
            servicioId = 1,
            descripcion = "Primer parcial",
            precio = 100.0
        )

    )


    YokairaTaveras_AP2_P1Theme() {
        ServicioListBody(
            servicios = servicios,
            onVerServicio = {},
            openDrawer = {},
            onEliminarServicio = {},
            onAddServicio = {},
        )
    }
}