package com.ucne.yokairataveras_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Parcial1NavHost(navHostController: NavHostController){
    NavHost(navController = navHostController,
        startDestination = Screen.List )
    {
        composable<Screen.List> {
            //Aqui va el componente de la   lista
        }
        composable<Screen.Registro> {  }
        //Aqui va el componente de la   lista

    }
}