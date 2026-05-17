package com.nammahasiru.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahasiru.data.PlantEntity
import com.nammahasiru.data.PlantRepository
import com.nammahasiru.data.PlantStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PlantDetailUiState(
    val plant: PlantEntity? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val saved: Boolean = false
)

class PlantDetailViewModel(private val repo: PlantRepository) : ViewModel() {
    private val _ui = MutableStateFlow(PlantDetailUiState())
    val ui: StateFlow<PlantDetailUiState> = _ui.asStateFlow()

    fun load(id: Long) {
        viewModelScope.launch {
            _ui.value = _ui.value.copy(isLoading = true, error = null, saved = false)
            val plant = repo.getPlant(id)
            _ui.value = _ui.value.copy(isLoading = false, plant = plant)
        }
    }

    fun updateStatus(status: PlantStatus) {
        val p = _ui.value.plant ?: return
        viewModelScope.launch {
            val updated = p.copy(status = status, lastUpdatedAtMillis = System.currentTimeMillis())
            repo.update(updated)
            _ui.value = _ui.value.copy(plant = updated, saved = true)
        }
    }

    fun updateGrowthPhoto(uri: String?) {
        val p = _ui.value.plant ?: return
        viewModelScope.launch {
            val updated = p.copy(growthPhotoUri = uri, lastUpdatedAtMillis = System.currentTimeMillis())
            repo.update(updated)
            _ui.value = _ui.value.copy(plant = updated, saved = true)
        }
    }

    fun consumeSavedFlag() {
        _ui.value = _ui.value.copy(saved = false)
    }
}

