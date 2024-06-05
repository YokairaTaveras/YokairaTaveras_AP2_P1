package com.ucne.yokairataveras_ap2_p1.data.local.dao

import com.ucne.yokairataveras_ap2_p1.data.local.entities.ServicioEntity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicioDao {
    @Upsert()
    suspend fun save(servicio: ServicioEntity)

    @Query(
        """
        SELECT * 
        FROM Servicios
        WHERE servicioId=:id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): ServicioEntity?

    @Delete
    suspend fun delete(servicio: ServicioEntity)

    @Query("SELECT * FROM Servicios")
    fun getAll(): Flow<List<ServicioEntity>>
}