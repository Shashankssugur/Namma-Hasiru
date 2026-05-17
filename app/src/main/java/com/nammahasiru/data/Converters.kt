package com.nammahasiru.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toPlantType(value: String?): PlantType? = value?.let { PlantType.valueOf(it) }

    @TypeConverter
    fun fromPlantType(value: PlantType?): String? = value?.name

    @TypeConverter
    fun toPlantStatus(value: String?): PlantStatus? = value?.let { PlantStatus.valueOf(it) }

    @TypeConverter
    fun fromPlantStatus(value: PlantStatus?): String? = value?.name
}

