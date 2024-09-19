package com.o2.sctrachapp.feature_scratchcard.presentation.activation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.o2.sctrachapp.R
import com.o2.sctrachapp.ui.components.ErrorDialog

@Composable
fun ActivationScreen(
    navController: NavController,
    viewModel: ActivationViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle(ActivationState())

    ActivationContentScreen(
        state = state,
        onActivateCard = viewModel::activateCard,
        onBack = navController::popBackStack,
        onDismissErrorDialog = viewModel::clearError,
    )
}

@Composable
private fun ActivationContentScreen(
    state: ActivationState,
    onActivateCard: () -> Unit,
    onBack: () -> Unit,
    onDismissErrorDialog: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(64.dp))
            Spacer(Modifier.size(12.dp))
            Text(
                text = stringResource(R.string.activation_in_progress),
                textAlign = TextAlign.Center,
            )
        }

        if (state.error != null) {
            ErrorDialog(stringResource(R.string.activation_error), onDismiss = onDismissErrorDialog)
        }

        if (state.activationSuccess) {
            Image(
                painter = painterResource(R.drawable.checked),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(Modifier.size(16.dp))
            Text(
                text = stringResource(R.string.activation_success),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )
        }

        Spacer(Modifier.size(32.dp))
        Text(text = stringResource(R.string.activation_info), textAlign = TextAlign.Center)
        Spacer(Modifier.size(16.dp))

        Button(onActivateCard, enabled = !state.isLoading && !state.activationSuccess) {
            Text(stringResource(R.string.activate_card))
        }

        Spacer(Modifier.size(16.dp))

        Button(onBack) {
            Text(stringResource(R.string.back))
        }
    }
}

@Composable
@Preview
private fun ActivationScreenPreview() {
    ActivationContentScreen(state = ActivationState(true), {}, {}, {})
}