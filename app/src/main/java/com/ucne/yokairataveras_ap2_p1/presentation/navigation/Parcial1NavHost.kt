package com.ucne.yokairataveras_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ucne.yokairataveras_ap2_p1.presentation.Servicio.ServicioListScreen
import com.ucne.yokairataveras_ap2_p1.presentation.Servicio.ServicioScreen

@Composable
fun Parcial1NavHost(
    navHostController: NavHostController,){
    NavHost(navController = navHostController,
        startDestination = Screen.ServicioListScreen )
    {
        composable<Screen.Servicio> {
            val args = it.toRoute<Screen.Servicio>()
            ServicioScreen(

                goToServicioList = { navHostController.navigate(Screen.ServicioListScreen) },

            )
        }
        composable<Screen.ServicioListScreen> {
            ServicioListScreen(

                onVerServicio = {
                    navHostController.navigate(
                        Screen.Servicio(
                            it.servicioId ?: 0
                        )
                    )
                },
                onAddServicio = {
                    navHostController.navigate(Screen.Servicio(0))
                }

            )
        }
    }
}