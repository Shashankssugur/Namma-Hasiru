package com.nammahasiru.data

import android.content.Context

/**
 * Small “manual DI” container to keep this project beginner-friendly.
 * (No Hilt/Dagger required.)
 */
class AppContainer(context: Context) {
    private val db: AppDatabase = AppDatabase.create(context.applicationContext)
    private val dao: PlantDao = db.plantDao()

    val plants: PlantRepository = PlantRepository(dao)
}

