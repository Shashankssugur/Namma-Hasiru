package com.nammahasiru.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class PlantType { SEED, SAPLING }
enum class PlantStatus { UNKNOWN, SURVIVED, DEAD }

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: PlantType,
    val datePlantedEpochDay: Long,
    val latitude: Double?,
    val longitude: Double?,
    val plantedPhotoUri: String?,
    val growthPhotoUri: String?,
    val status: PlantStatus,
    val reminderSent: Boolean,
    val lastUpdatedAtMillis: Long
)

