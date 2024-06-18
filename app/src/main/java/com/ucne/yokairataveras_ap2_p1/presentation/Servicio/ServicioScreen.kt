package com.ucne.yokairataveras_ap2_p1.presentation.Servicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.yokairataveras_ap2_p1.ui.theme.YokairaTaveras_AP2_P1Theme


@Composable
fun ServicioScreen(
    viewModel: ServicioViewModel = hiltViewModel(),
    goToServicioList: () -> Unit
) {

    val Servicio by viewModel.servicio.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ServicioBody(
        uiState = uiState,
        onSaveServicio = {
            viewModel.saveServicio()
        },
        onDeleteServicio = {
            viewModel.deleteServicio()
        },
        goToServicioList = goToServicioList,
        onNewServicio = {
            viewModel.newServicio()
        },
        onDescripcionChanged = viewModel::onDescripcionChanged,
        onPrecioChanged = viewModel::onPrecioChanged,

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ServicioBody(
    uiState: ServicioUIState,
    onSaveServicio: () -> Boolean,
    goToServicioList: () -> Unit,
    onDeleteServicio: () -> Unit = {},
    onDescripcionChanged: (String) -> Unit,
    onNewServicio: () -> Unit,
    onPrecioChanged: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Servicio")
                }
            )
        }

    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(4.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    OutlinedTextField(
                        label = { Text(text = "Descripción") },
                        value = uiState.descripcion ?: "",
                        onValueChange = onDescripcionChanged,
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.descripcionError != null
                    )
                    if (uiState.descripcionError != null) {
                        Text(
                            text = uiState.descripcionError ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    Spacer(modifier = Modifier.padding(2.dp))

                    OutlinedTextField(
                        label = { Text(text = "Precio") },
                        value = uiState.precio.toString().replace("null", ""),
                        onValueChange = onPrecioChanged,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.precioError != null
                    )
                    if (uiState.precioError != null) {
                        Text(
                            text = uiState.precioError ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Spacer(modifier = Modifier.padding(2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(onClick = onNewServicio) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "new button"
                            )
                            Text(text = "Nuevo")
                        }
                        OutlinedButton(
                            onClick = {
                                if (onSaveServicio()) {
                                    goToServicioList()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "save button"
                            )
                            Text(text = "Guardar")
                        }
                        OutlinedButton(
                            onClick = {
                                onDeleteServicio()
                                goToServicioList()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete button"
                            )
                            Text(text = "Borrar")
                        }
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun ServicioPreview() {
    YokairaTaveras_AP2_P1Theme() {
        ServicioBody(
            uiState = ServicioUIState(),
            onSaveServicio = { true },
            goToServicioList = {},
            onDeleteServicio = {},
            onDescripcionChanged = {},
            onNewServicio = {},
            onPrecioChanged = {}
        )
    }
}
