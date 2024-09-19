package com.o2.sctrachapp.feature_scratchcard.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.o2.sctrachapp.R
import com.o2.sctrachapp.feature_scratchcard.domain.model.ScratchCardModel
import com.o2.sctrachapp.feature_scratchcard.presentation.components.ScratchCard
import com.o2.sctrachapp.feature_scratchcard.presentation.util.ScreenRoute

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.cardFlow.collectAsStateWithLifecycle(ScratchCardModel())

    HomeScreenContent(
        state = state,
        onScratchCard = {
            navController.navigate(ScreenRoute.ScratchCardScreen.route)
        },
        onActivateCard = {
            navController.navigate(ScreenRoute.ActivationScreen.route)
        }
    )
}

@Composable
private fun HomeScreenContent(
    state: ScratchCardModel,
    onScratchCard: () -> Unit,
    onActivateCard: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val isScratched =
            if (state.isScratched) stringResource(R.string.yes) else stringResource(R.string.no)
        val isActivated =
            if (state.isActivated) stringResource(R.string.yes) else stringResource(R.string.no)

        ScratchCard(card = state)
        Spacer(modifier = Modifier.size(16.dp))

        Text(
            stringResource(R.string.card_scratched, isScratched),
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            stringResource(R.string.card_activated, isActivated),
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.size(16.dp))

        Button(onClick = onScratchCard, enabled = !state.isScratched) {
            Text(text = stringResource(R.string.scratch_card))
        }

        Spacer(modifier = Modifier.size(16.dp))

        Button(onClick = onActivateCard, enabled = state.isScratched && !state.isActivated) {
            Text(text = stringResource(R.string.activate_card))
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val scratchCardModel = ScratchCardModel(isActivated = true, isScratched = true, code = "")
    HomeScreenContent(scratchCardModel, {}, {})
}