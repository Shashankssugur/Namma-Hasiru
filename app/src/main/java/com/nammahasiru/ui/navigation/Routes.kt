package com.nammahasiru.ui.navigation

object Routes {
    const val HOME = "home"
    const val ADD = "add"
    const val PLANTS = "plants"
    const val GUIDE = "guide"

    const val DETAIL = "detail"
    const val DETAIL_WITH_ARG = "detail/{plantId}"

    fun detail(plantId: Long) = "detail/$plantId"
}

