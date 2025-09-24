package com.example.conectaribas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.conectaribas.ui.screens.HomeScreen
import com.example.conectaribas.ui.screens.DiagnosisScreen
import com.example.conectaribas.ui.screens.FirstAidScreen
import com.example.conectaribas.ui.screens.HistoryScreen
import com.example.conectaribas.ui.screens.SettingsScreen
import com.example.conectaribas.ui.theme.ConectaRibasTheme
import com.example.conectaribas.ui.viewmodel.MainViewModel

/**
 * MainActivity principal da Aplicação
 * Configura a navegação entre as diferentes telas da aplicação
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConectaRibasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConectaRibasApp()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

    }
}

/**
 * Aplicação principal com navegação configurada
 */
@Composable
fun ConectaRibasApp() {
    val navController = rememberNavController()

    // ViewModel para gerenciar o estado da aplicação
    val viewModel: MainViewModel = viewModel {
        val app = navController.context.applicationContext as ConectaRibasApplication
        MainViewModel(app.repository)
    }


    val symptomHistory by viewModel.symptomHistory.collectAsState()

    viewModel.loadInitialData();

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToDiagnosis = { navController.navigate("diagnosis") },
                onNavigateToFirstAid = { navController.navigate("first_aid") },
                onNavigateToHistory = { navController.navigate("history") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }

        // Tela de autodiagnóstico
        composable("diagnosis") {
            DiagnosisScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onSaveDiagnosis = { patientName, symptoms, diagnosis, recommendations ->
                    // Salva diretamente no banco de dados através do ViewModel
                    viewModel.saveSymptomRecord(patientName, symptoms, diagnosis, recommendations)
                }
            )
        }

        // Tela de primeiros socorros
        composable("first_aid") {
            FirstAidScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } }
            )
        }

        // Tela de histórico
        composable("history") {
            HistoryScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                symptomHistory = symptomHistory,
                onClearHistory = { viewModel.clearAllSymptomRecords() }
            )
        }

        // Tela de configurações
        composable("settings") {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } }
            )
        }
    }
}