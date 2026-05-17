package com.nammahasiru.data

import kotlinx.coroutines.flow.Flow

class PlantRepository(private val dao: PlantDao) {
    fun allPlants(): Flow<List<PlantEntity>> = dao.getAllFlow()
    fun recentPlants(limit: Int = 5): Flow<List<PlantEntity>> = dao.getRecentFlow(limit)

    suspend fun getPlant(id: Long): PlantEntity? = dao.getById(id)
    suspend fun upsert(plant: PlantEntity): Long = dao.upsert(plant)
    suspend fun update(plant: PlantEntity) = dao.update(plant)

    suspend fun unknownUnreminded(): List<PlantEntity> = dao.getUnremindedUnknownPlants()
}

