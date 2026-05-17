package com.nammahasiru.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahasiru.data.PlantEntity
import com.nammahasiru.data.PlantRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PlantsViewModel(repo: PlantRepository) : ViewModel() {
    val plants: StateFlow<List<PlantEntity>> =
        repo.allPlants().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}

