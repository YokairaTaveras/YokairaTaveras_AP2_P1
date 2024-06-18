package com.ucne.yokairataveras_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ucne.yokairataveras_ap2_p1.data.local.database.ServicioDb
import com.ucne.yokairataveras_ap2_p1.presentation.navigation.Parcial1NavHost
import com.ucne.yokairataveras_ap2_p1.ui.theme.YokairaTaveras_AP2_P1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var servicioDb: ServicioDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            YokairaTaveras_AP2_P1Theme {
                val navHost = rememberNavController()

                Parcial1NavHost(navHost)

            }
        }
    }
}
