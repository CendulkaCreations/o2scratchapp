package com.o2.sctrachapp.feature_scratchcard.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.o2.sctrachapp.R
import com.o2.sctrachapp.feature_scratchcard.domain.model.ScratchCardModel

@Composable
fun ScratchCard(
    card: ScratchCardModel,
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    animateScratching: Boolean = false,
    isLoading: Boolean = false,
) {
    val scratchBoxColor by animateColorAsState(
        targetValue = when {
            animateScratching -> lerp(Color.DarkGray, Color.LightGray, progress)
            card.isScratched -> Color.LightGray
            else -> Color.DarkGray
        },
        label = ""
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                color = Color.Cyan,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Spacer(modifier = Modifier.size(8.dp))
        Image(
            painter = painterResource(R.drawable.trophy),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp)
                .background(scratchBoxColor),
        ) {
            if (card.isScratched) {
                Text(
                    card.code,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            if (animateScratching && isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}

@Composable
@Preview
private fun CardScratchedPreview() {
    ScratchCard(card = ScratchCardModel(isScratched = true, code = "code"))
}

@Composable
@Preview
private fun CardUnScratchedPreview() {
    ScratchCard(card = ScratchCardModel(isScratched = false))
}