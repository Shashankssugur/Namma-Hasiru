package com.nammahasiru.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(plant: PlantEntity): Long

    @Update
    suspend fun update(plant: PlantEntity)

    @Query("SELECT * FROM plants ORDER BY datePlantedEpochDay DESC, id DESC")
    fun getAllFlow(): Flow<List<PlantEntity>>

    @Query("SELECT * FROM plants ORDER BY lastUpdatedAtMillis DESC LIMIT :limit")
    fun getRecentFlow(limit: Int = 5): Flow<List<PlantEntity>>

    @Query("SELECT * FROM plants WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): PlantEntity?

    @Query(
        """
        SELECT * FROM plants 
        WHERE status = 'UNKNOWN'
        AND reminderSent = 0
        """
    )
    suspend fun getUnremindedUnknownPlants(): List<PlantEntity>
}

