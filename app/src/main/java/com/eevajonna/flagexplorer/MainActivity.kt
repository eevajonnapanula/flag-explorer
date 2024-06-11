package com.eevajonna.flagexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eevajonna.flagexplorer.ui.FlagsViewModel
import com.eevajonna.flagexplorer.ui.screen.DetailScreen
import com.eevajonna.flagexplorer.ui.screen.MainScreen
import com.eevajonna.flagexplorer.ui.theme.FlagExplorerTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlagExplorerTheme {
                val viewModel: FlagsViewModel by viewModels()
                val state by viewModel.state.collectAsState()

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                LaunchedEffect(Unit) {
                    viewModel.getFlags()
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Flags") },
                            navigationIcon = {
                                if (navBackStackEntry?.destination?.route != Routes.Home.route) {
                                    IconButton(onClick = { navController.navigateUp() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Navigate up",
                                        )
                                    }
                                }
                            },
                        )
                    },
                ) { innerPadding ->
                    NavHost(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .padding(16.dp),
                        navController = navController,
                        startDestination = Routes.Home.route,
                    ) {
                        composable(Routes.Home.route) {
                            MainScreen(
                                flags = state.flags,
                                loading = state.loading,
                            ) {
                                viewModel.setFlag(it)
                                navController.navigate(Routes.Details.route)
                            }
                        }
                        composable(Routes.Details.route) {
                            DetailScreen(flag = state.currentFlag)
                        }
                    }
                }
            }
        }
    }
}

object Routes {
    object Details {
        const val route = "detail"
    }

    object Home {
        const val route = "home"
    }
}
