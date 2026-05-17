package com.nammahasiru.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammahasiru.data.PlantEntity
import com.nammahasiru.data.PlantRepository
import com.nammahasiru.data.PlantStatus
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class HomeSummary(
    val totalPlants: Int = 0,
    val survivalRatePercent: Int = 0,
    val recent: List<PlantEntity> = emptyList()
)

class HomeViewModel(repo: PlantRepository) : ViewModel() {
    private val allFlow = repo.allPlants()
    private val recentFlow = repo.recentPlants(limit = 5)

    val summary: StateFlow<HomeSummary> =
        combine(allFlow, recentFlow) { plants, recent ->
            val total = plants.size
            val survived = plants.count { it.status == PlantStatus.SURVIVED }
            val decided = plants.count { it.status != PlantStatus.UNKNOWN }
            val rate = if (decided == 0) 0 else ((survived * 100.0) / decided).toInt()
            HomeSummary(
                totalPlants = total,
                survivalRatePercent = rate,
                recent = recent
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeSummary())
}

