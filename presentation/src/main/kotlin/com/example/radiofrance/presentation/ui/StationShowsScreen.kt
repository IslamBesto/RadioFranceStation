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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.radiofrance.presentation.R
import com.example.radiofrancestation.presentation.theme.Spacing.Spacing16dp
import com.example.radiofrancestation.presentation.theme.Spacing.Spacing4dp
import com.example.radiofrancestation.presentation.theme.Spacing.Spacing8dp
import com.example.radiofrance.presentation.viewmodel.ShowsViewModel
import com.example.radiofrance.presentation.viewmodel.state.ShowUiModel
import com.example.radiofrance.presentation.viewmodel.state.ShowsUiState
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
                title = { Text(text = stringResource(R.string.shows_toolbar_title)) }
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
                Text(stringResource(R.string.loading))
            }
        }

        is ShowsUiState.Success -> LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(Spacing16dp),
            verticalArrangement = Arrangement.spacedBy(Spacing16dp)
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
            .background(color = Color(0xFF292B35), shape = RoundedCornerShape(Spacing16dp))
            .padding(Spacing16dp)
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

            Spacer(modifier = Modifier.width(Spacing8dp))
            if (show.episodeCount > 0) {
                Text(
                    text = stringResource(
                        R.string.show_episode_count,
                        show.episodeCount
                    ),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                    modifier = Modifier
                        .background(Color(0xFF52545B), shape = RoundedCornerShape(Spacing8dp))
                        .padding(horizontal = Spacing8dp, vertical = Spacing4dp)
                )
            }
        }
    }

}
