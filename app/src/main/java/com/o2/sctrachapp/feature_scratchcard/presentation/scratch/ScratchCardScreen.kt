package com.o2.sctrachapp.feature_scratchcard.presentation.scratch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.o2.sctrachapp.R
import com.o2.sctrachapp.feature_scratchcard.domain.model.ScratchCardModel
import com.o2.sctrachapp.feature_scratchcard.presentation.components.ScratchCard

@Composable
fun ScratchCardScreen(
    navController: NavController,
    viewModel: ScratchCardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(ScratchCardState())

    ScratchCardScreenContent(
        state = state,
        onScratchCard = viewModel::generateCode,
        onNavigateBack = navController::popBackStack
    )
}

@Composable
private fun ScratchCardScreenContent(
    state: ScratchCardState,
    onScratchCard: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(text = stringResource(R.string.scratch_info), textAlign = TextAlign.Center)
        Spacer(Modifier.size(16.dp))

        val progress = calculateProgress(state)

        ScratchCard(
            state.card,
            progress = progress,
            animateScratching = true,
            isLoading = state.isLoading
        )

        Spacer(Modifier.size(16.dp))
        Button(onScratchCard, enabled = !state.card.isScratched) {
            Text(stringResource(R.string.scratch_card))
        }

        Spacer(Modifier.size(16.dp))
        Button(onNavigateBack) {
            Text(stringResource(R.string.back))
        }

    }
}

private fun calculateProgress(state: ScratchCardState): Float {
    return if (state.isLoading) 0.5f else 1.0f
}

@Preview
@Composable
private fun ScratchCardScreenPreview() {
    val card = ScratchCardModel(isActivated = false, isScratched = false, code = "code")
    ScratchCardScreenContent(ScratchCardState(isLoading = false, card), {}, {})
}