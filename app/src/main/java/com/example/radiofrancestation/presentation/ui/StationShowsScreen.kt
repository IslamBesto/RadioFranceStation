package com.example.radiofrancestation.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import com.example.radiofrancestation.presentation.viewmodel.ShowsViewModel
import com.example.radiofrancestation.presentation.viewmodel.state.ShowUiModel
import com.example.radiofrancestation.presentation.viewmodel.state.ShowsUiState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationShowsScreen(
    modifier: Modifier = Modifier,
    viewModel: ShowsViewModel = koinViewModel<ShowsViewModel>()
) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Programmes") }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        StationShowsScreen(
            modifier = modifier.padding(innerPadding),
            showsUiState = uiState.value,
        )
    }
}

@Composable
private fun StationShowsScreen(
    modifier: Modifier = Modifier,
    showsUiState: ShowsUiState,
) {
    when (showsUiState) {
        is ShowsUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Loading...")
            }
        }

        is ShowsUiState.Success -> LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(showsUiState.shows) { show ->
                ShowItem(show = show)
            }
        }
    }
}

@Composable
private fun ShowItem(show: ShowUiModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF292B35), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = show.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))
            if(show.episodeCount > 0) {
                Text(
                    text = "${show.episodeCount} Episodes",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                    modifier = Modifier
                        .background(Color(0xFF52545B), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }

}
