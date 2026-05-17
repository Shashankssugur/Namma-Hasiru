package com.nammahasiru.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nammahasiru.NammaHasiruApp
import com.nammahasiru.data.PlantRepository

class AppViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    private val repo: PlantRepository = (app as NammaHasiruApp).container.plants

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repo) as T
            modelClass.isAssignableFrom(AddPlantViewModel::class.java) -> AddPlantViewModel(repo) as T
            modelClass.isAssignableFrom(PlantsViewModel::class.java) -> PlantsViewModel(repo) as T
            modelClass.isAssignableFrom(PlantDetailViewModel::class.java) -> PlantDetailViewModel(repo) as T
            modelClass.isAssignableFrom(GuideViewModel::class.java) -> GuideViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
        }
    }
}

