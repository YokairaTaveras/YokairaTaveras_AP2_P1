package com.ucne.yokairataveras_ap2_p1.data.local.remote


import com.ucne.yokairataveras_ap2_p1.data.local.remote.dto.PersonaDto
import retrofit2.http.GET

interface PersonaApi {
    @GET("api/Personas")
    suspend fun getPersona(): List<PersonaDto>

}
