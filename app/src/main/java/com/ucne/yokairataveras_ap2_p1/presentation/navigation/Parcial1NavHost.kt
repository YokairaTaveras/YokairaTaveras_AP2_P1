package com.ucne.yokairataveras_ap2_p1.presentation.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ucne.yokairataveras_ap2_p1.presentation.Servicio.ServicioListScreen
import com.ucne.yokairataveras_ap2_p1.presentation.Servicio.ServicioScreen
import com.ucne.yokairataveras_ap2_p1.presentation.Servicio.ServicioViewModel
import com.ucne.yokairataveras_ap2_p1.repository.ServicioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Parcial1NavHost(
    navHostController: NavHostController,
    repository: ServicioRepository,
    scope: CoroutineScope,
    drawerState: DrawerState,){
    NavHost(navController = navHostController,
        startDestination = Screen.ServicioListScreen )
    {
        composable<Screen.Servicio> {
            val args = it.toRoute<Screen.Servicio>()
            ServicioScreen(
                viewModel = viewModel {
                    ServicioViewModel(
                       repository,
                        args.servicioId
                    )
                },
                goToServicioList = { navHostController.navigate(Screen.ServicioListScreen) },
                openDrawer = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        }
        composable<Screen.ServicioListScreen> {
            ServicioListScreen(
                viewModel = viewModel { ServicioViewModel(repository, 0) },
                onVerServicio = {
                    navHostController.navigate(
                        Screen.Servicio(
                            it.servicioId ?: 0
                        )
                    )
                },
                onAddServicio = {
                    navHostController.navigate(Screen.Servicio(0))
                },
                openDrawer = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    }
}