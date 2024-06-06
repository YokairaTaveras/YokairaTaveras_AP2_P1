package com.ucne.yokairataveras_ap2_p1.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Screen {
    @kotlinx.serialization.Serializable
    class Servicio(val servicioId: Int) : Screen()

    @Serializable
    object ServicioListScreen : Screen()
}
