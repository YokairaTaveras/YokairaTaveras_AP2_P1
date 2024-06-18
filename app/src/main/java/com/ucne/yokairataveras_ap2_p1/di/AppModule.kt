package com.ucne.yokairataveras_ap2_p1.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ucne.yokairataveras_ap2_p1.data.local.dao.ServicioDao
import com.ucne.yokairataveras_ap2_p1.data.local.database.ServicioDb
import com.ucne.yokairataveras_ap2_p1.data.local.remote.PersonaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideServicioDatabase(@ApplicationContext  appContext: Context): ServicioDb{
        return Room.databaseBuilder(
            appContext,
            ServicioDb::class.java,
            "servicio.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    fun provideServicioDao(db: ServicioDb): ServicioDao = db.servicioDao()

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesPersonaApi(moshi: Moshi): PersonaApi {
        return Retrofit.Builder()
            .baseUrl("https://ap2ticket.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PersonaApi::class.java)
    }
}

