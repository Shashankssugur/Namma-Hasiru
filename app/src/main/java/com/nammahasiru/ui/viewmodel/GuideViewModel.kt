package com.nammahasiru.ui.viewmodel

import androidx.lifecycle.ViewModel

enum class SoilType { RED, BLACK, SANDY, CLAY, LOAMY }

data class SpeciesSuggestion(
    val name: String,
    val why: String
)

class GuideViewModel : ViewModel() {
    fun suggestionsFor(soil: SoilType): List<SpeciesSuggestion> {
        return when (soil) {
            SoilType.RED -> listOf(
                SpeciesSuggestion("Neem", "Hardy tree; tolerates drier red soils."),
                SpeciesSuggestion("Tamarind", "Deep roots; good for semi-arid regions.")
            )
            SoilType.BLACK -> listOf(
                SpeciesSuggestion("Banyan", "Thrives in moisture-retentive black soil."),
                SpeciesSuggestion("Jamun", "Good growth with rich, clay-like soils.")
            )
            SoilType.SANDY -> listOf(
                SpeciesSuggestion("Casuarina", "Handles sandy/coastal soils well."),
                SpeciesSuggestion("Coconut", "Suitable for sandy soils with water access.")
            )
            SoilType.CLAY -> listOf(
                SpeciesSuggestion("Indian Rosewood (Beete)", "Grows well with consistent moisture."),
                SpeciesSuggestion("Arjuna", "Prefers heavier soils near water sources.")
            )
            SoilType.LOAMY -> listOf(
                SpeciesSuggestion("Mango", "Loamy soil supports strong root development."),
                SpeciesSuggestion("Guava", "Easy to grow and productive in loamy soils.")
            )
        }
    }
}

