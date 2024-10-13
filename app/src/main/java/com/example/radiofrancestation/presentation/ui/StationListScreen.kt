package com.example.radiofrancestation.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.radiofrancestation.presentation.Routes
import com.example.radiofrancestation.presentation.createRoute
import com.example.radiofrancestation.presentation.theme.RadioFranceStationTheme
import com.example.radiofrancestation.presentation.viewmodel.StationsViewModel
import com.example.radiofrancestation.presentation.viewmodel.state.StationUiModel
import com.example.radiofrancestation.presentation.viewmodel.state.StationsUiState
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: StationsViewModel = koinViewModel<StationsViewModel>()
) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Stations") }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        StationListScreen(
            modifier = modifier.padding(innerPadding),
            stationUiState = uiState.value,
            navController = navController
        )
    }
}

@Composable
private fun StationListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    stationUiState: StationsUiState
) {
    when (stationUiState) {
        is StationsUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Loading...")
            }
        }

        is StationsUiState.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(stationUiState.stations) { station ->
                    StationItem(station = station, onClick = { backgroundColor ->
                        val colorHex = backgroundColor.toHex()
                        navController.navigate(Routes.SHOWS.createRoute(station.id))
                    })
                }
            }
        }
    }
}

@Composable
private fun StationItem(station: StationUiModel, onClick: (backgroundColor: Color) -> Unit) {
    val backgroundColor = rememberRandomColor()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
            .clickable { onClick(backgroundColor) }
    ) {
        Column {
            Text(
                text = station.title,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = station.baseLine.orEmpty(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun rememberRandomColor(): Color {
    val random = Random.Default
    return Color(
        red = random.nextFloat(),
        green = random.nextFloat(),
        blue = random.nextFloat(),
        alpha = 1f
    )
}

private fun Color.toHex(): String {
    return String.format("#%06X", 0xFFFFFF and this.value.toInt())
}

@Preview(showBackground = true)
@Composable
fun PreviewStationList() {
    val sampleStations = listOf(
        StationUiModel("1", "Figma Basic", "8 Hours | 4.9 Rating"),
        StationUiModel("2", "Graphic Design", "6 Hours | 4.8 Rating")
    )

    RadioFranceStationTheme {
        StationListScreen(
            navController = rememberNavController(),
            stationUiState = StationsUiState.Success(sampleStations)
        )
    }
}
