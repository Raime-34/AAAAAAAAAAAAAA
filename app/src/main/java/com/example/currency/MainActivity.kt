package com.example.currency

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currency.presentation.main_screen.MainScreenEvent
import com.example.currency.presentation.main_screen.MainScreenViewModel
import com.example.currency.presentation.main_screen.components.CurrencyProfileScreen
import com.example.currency.presentation.main_screen.components.CurrentSourcePicker
import com.example.currency.presentation.main_screen.components.MainScreen
import com.example.currency.presentation.main_screen.components.MainScreenItem
import com.example.currency.ui.theme.CurrencyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainScreenViewModel = hiltViewModel()
            val state = viewModel.state.value
            val navController = rememberNavController()

            CurrencyTheme {
                Scaffold {it ->
                    NavHost(navController = navController, startDestination = "mainScreen"){
                        composable("mainScreen"){
                            MainScreen(state = state, viewModel) {
                                navController.navigate("currencyProfile/$it")
                            }
                        }
                        composable("currencyProfile/{id}") { backStackEntry ->
                            backStackEntry.arguments?.getInt("id")
                                ?.let { id -> CurrencyProfileScreen(itemState = state.currencyList[id], state.currentSource) }
                        }
                    }
                }
            }
        }
    }
}