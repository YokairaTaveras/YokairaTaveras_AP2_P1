package com.ucne.yokairataveras_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.ucne.yokairataveras_ap2_p1.data.local.database.ServicioDb
import com.ucne.yokairataveras_ap2_p1.presentation.components.DrawerNavigation
import com.ucne.yokairataveras_ap2_p1.presentation.navigation.Parcial1NavHost
import com.ucne.yokairataveras_ap2_p1.presentation.navigation.Screen
import com.ucne.yokairataveras_ap2_p1.repository.ServicioRepository
import com.ucne.yokairataveras_ap2_p1.ui.theme.YokairaTaveras_AP2_P1Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var servicioDb: ServicioDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        servicioDb = Room.databaseBuilder(
            this,
            ServicioDb::class.java,
            "servicio.db"
        )
            .fallbackToDestructiveMigration()
            .build()
        val repository = ServicioRepository(servicioDb.servicioDao())
        enableEdgeToEdge()
        setContent {

            YokairaTaveras_AP2_P1Theme {
                val navHost = rememberNavController()
                val scope = rememberCoroutineScope()
                var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                DrawerNavigation(
                    drawerState = drawerState,
                    navToServicioList = {navHost.navigate(Screen.ServicioListScreen)},
                    closeDrawer = {
                        scope.launch {
                            drawerState.close()
                        }
                    },
                )
                Parcial1NavHost(navHost,repository, scope, drawerState)

            }
        }
    }
}
