package com.nammahasiru.ui

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nammahasiru.ui.navigation.Routes
import com.nammahasiru.ui.screens.AddPlantScreen
import com.nammahasiru.ui.screens.GuideScreen
import com.nammahasiru.ui.screens.HomeScreen
import com.nammahasiru.ui.screens.PlantDetailScreen
import com.nammahasiru.ui.screens.PlantsScreen
import com.nammahasiru.ui.viewmodel.AddPlantViewModel
import com.nammahasiru.ui.viewmodel.AppViewModelFactory
import com.nammahasiru.ui.viewmodel.GuideViewModel
import com.nammahasiru.ui.viewmodel.HomeViewModel
import com.nammahasiru.ui.viewmodel.PlantDetailViewModel
import com.nammahasiru.ui.viewmodel.PlantsViewModel

@Composable
fun AppRoot() {
    val nav = rememberNavController()
    val context = LocalContext.current
    val factory = AppViewModelFactory(context.applicationContext as Application)

    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            val showBottom = currentRoute in setOf(Routes.HOME, Routes.ADD, Routes.PLANTS, Routes.GUIDE)
            if (showBottom) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == Routes.HOME,
                        onClick = { nav.navigateSingleTop(Routes.HOME) },
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Routes.ADD,
                        onClick = { nav.navigateSingleTop(Routes.ADD) },
                        icon = { Icon(Icons.Filled.AddCircle, contentDescription = null) },
                        label = { Text("Add") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Routes.PLANTS,
                        onClick = { nav.navigateSingleTop(Routes.PLANTS) },
                        icon = { Icon(Icons.Filled.Forest, contentDescription = null) },
                        label = { Text("Plants") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Routes.GUIDE,
                        onClick = { nav.navigateSingleTop(Routes.GUIDE) },
                        icon = { Icon(Icons.Filled.Book, contentDescription = null) },
                        label = { Text("Guide") }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.HOME) {
                val vm: HomeViewModel = viewModel(factory = factory)
                HomeScreen(
                    vm = vm,
                    onOpenPlant = { nav.navigate(Routes.detail(it)) }
                )
            }
            composable(Routes.ADD) {
                val vm: AddPlantViewModel = viewModel(factory = factory)
                AddPlantScreen(
                    vm = vm,
                    onSaved = { id -> nav.navigate(Routes.detail(id)) }
                )
            }
            composable(Routes.PLANTS) {
                val vm: PlantsViewModel = viewModel(factory = factory)
                PlantsScreen(
                    vm = vm,
                    onOpenPlant = { nav.navigate(Routes.detail(it)) }
                )
            }
            composable(Routes.GUIDE) {
                val vm: GuideViewModel = viewModel(factory = factory)
                GuideScreen(vm = vm)
            }
            composable(
                route = Routes.DETAIL_WITH_ARG,
                arguments = listOf(navArgument("plantId") { type = NavType.LongType })
            ) { entry ->
                val plantId = entry.arguments?.getLong("plantId") ?: 0L
                val vm: PlantDetailViewModel = viewModel(factory = factory)
                PlantDetailScreen(
                    plantId = plantId,
                    vm = vm,
                    onBack = { nav.popBackStack() }
                )
            }
        }
    }
}

private fun androidx.navigation.NavHostController.navigateSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

