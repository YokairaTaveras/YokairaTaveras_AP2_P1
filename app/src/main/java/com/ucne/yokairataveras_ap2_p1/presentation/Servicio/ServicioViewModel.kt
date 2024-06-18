package com.ucne.yokairataveras_ap2_p1.presentation.Servicio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.yokairataveras_ap2_p1.data.local.entities.ServicioEntity
import com.ucne.yokairataveras_ap2_p1.data.local.remote.dto.PersonaDto
import com.ucne.yokairataveras_ap2_p1.data.local.repository.PersonaRepository
import com.ucne.yokairataveras_ap2_p1.data.local.repository.Resource
import com.ucne.yokairataveras_ap2_p1.data.local.repository.ServicioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicioViewModel @Inject constructor (
    private val servicioRepository: ServicioRepository,
    private val personaRepository: PersonaRepository
) : ViewModel() {
    private var servicioId: Int = 0
    var uiState = MutableStateFlow(ServicioUIState())
        private set

    val servicio = servicioRepository.getServicios()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun onDescripcionChanged(descripcion: String) {
        uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    fun onPrecioChanged(total: String) {
        val letras = Regex("[a-zA-Z ]+")
        val numeros= total.replace(letras, "").toDouble()
        uiState.update {
            it.copy(precio = numeros)
        }
    }

    init {
        viewModelScope.launch {
            val servicio = servicioRepository.getServicio(servicioId)

            servicio?.let {
                uiState.update {
                    it.copy(
                        servicioId = servicio.servicioId,
                        descripcion = servicio.descripcion?: "",
                        precio = servicio.precio,
                        )
                }
            }
            getPersona()
        }
    }

    fun saveServicio(): Boolean {

        viewModelScope.launch {
            if(Validar()){
                servicioRepository.saveServicio(uiState.value.toEntity())
                uiState.value = ServicioUIState()
            }
        }
        return Validar()
    }

    fun newServicio() {
        viewModelScope.launch {
            uiState.value = ServicioUIState()
        }
    }

    fun deleteServicio() {
        viewModelScope.launch {
            servicioRepository.deleteServicio(uiState.value.toEntity())
        }
    }



    fun Validar(): Boolean {
        val descripcionVacia = uiState.value.descripcion.isNullOrEmpty() || uiState.value.descripcion?.isBlank() ?: false
        val precioVacio = (uiState.value.precio ?: 0.0) <= 0.0
        if (descripcionVacia) {
            uiState.update {
                it.copy(descripcionError = "Debes de ingresar una descripcion")
            }
        }
        else{
            uiState.update {
                it.copy(descripcionError = null)
            }
        }
        if(precioVacio){
            uiState.update {
                it.copy(precioError = "Debes de ingresar un precio")
            }
        }
        else{
            uiState.update {
                it.copy(precioError = null)
            }
        }



        return !descripcionVacia && !precioVacio
    }

    fun getPersona() {
        viewModelScope.launch {
            personaRepository.getPersona().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        uiState.update {
                            it.copy(isLoading = true)
                        }

                    }

                    is Resource.Success -> {
                        uiState.update {
                            it.copy(
                                isLoading = false,
                                persona = result.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Error -> {
                        uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            }
        }
    }
}


data class ServicioUIState(
    val servicioId: Int? = null,
    val descripcion: String? = "",
    var descripcionError: String? = null,
    val precio: Double? = null,
    var precioError: String? = null,
    val persona: List<PersonaDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

fun ServicioUIState.toEntity() = ServicioEntity(
    servicioId = servicioId,
    descripcion = descripcion,
    precio = precio,
)