package com.nammahasiru.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahasiru.data.PlantEntity
import com.nammahasiru.data.PlantRepository
import com.nammahasiru.data.PlantStatus
import com.nammahasiru.data.PlantType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

data class AddPlantUiState(
    val name: String = "",
    val type: PlantType = PlantType.SAPLING,
    val plantedDate: LocalDate = LocalDate.now(),
    val latitude: Double? = null,
    val longitude: Double? = null,
    val plantedPhotoUri: String? = null,
    val isSaving: Boolean = false,
    val savedId: Long? = null,
    val error: String? = null
)

class AddPlantViewModel(private val repo: PlantRepository) : ViewModel() {
    private val _ui = MutableStateFlow(AddPlantUiState())
    val ui: StateFlow<AddPlantUiState> = _ui.asStateFlow()

    fun setName(v: String) = _ui.value.let { _ui.value = it.copy(name = v, error = null) }
    fun setType(v: PlantType) = _ui.value.let { _ui.value = it.copy(type = v) }
    fun setDate(v: LocalDate) = _ui.value.let { _ui.value = it.copy(plantedDate = v) }
    fun setLocation(lat: Double?, lon: Double?) = _ui.value.let { _ui.value = it.copy(latitude = lat, longitude = lon) }
    fun setPhotoUri(uri: String?) = _ui.value.let { _ui.value = it.copy(plantedPhotoUri = uri) }
    fun clearSavedFlag() = _ui.value.let { _ui.value = it.copy(savedId = null) }

    fun savePlant() {
        val current = _ui.value
        if (current.name.isBlank()) {
            _ui.value = current.copy(error = "Please enter a plant name.")
            return
        }

        viewModelScope.launch {
            _ui.value = current.copy(isSaving = true, error = null)
            try {
                val now = System.currentTimeMillis()
                val id = repo.upsert(
                    PlantEntity(
                        name = current.name.trim(),
                        type = current.type,
                        datePlantedEpochDay = current.plantedDate.toEpochDay(),
                        latitude = current.latitude,
                        longitude = current.longitude,
                        plantedPhotoUri = current.plantedPhotoUri,
                        growthPhotoUri = null,
                        status = PlantStatus.UNKNOWN,
                        reminderSent = false,
                        lastUpdatedAtMillis = now
                    )
                )
                _ui.value = AddPlantUiState(savedId = id)
            } catch (t: Throwable) {
                _ui.value = current.copy(isSaving = false, error = t.message ?: "Save failed")
            }
        }
    }
}

